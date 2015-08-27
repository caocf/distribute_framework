package org.durcframework.open;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 校验器,签名校验
 * @author hc.tang
 *
 */
public abstract class OpenValidater implements Validater {
	
	private static final String APP_ID_NAME = "appId";
	private static final String SIGN_NAME = "sign";
	private static final String TIMESTAMP_NAME = "timestamp";

	private Logger logger = Logger.getLogger(getClass());

	private AppSecretManager appSecretManager;
	
	@Override
	public boolean validate(HttpServletRequest request,HttpServletResponse response,Object handler) {
		
		try{
			logger.info("^^^客户端请求URL:" + getFullUrl(request));
			
			if(isMatch(request, response,handler)) {
				RequestContext requestContext = this.buildApiRequestContext(request, response,handler);
				validate(requestContext);
			}
			
			return true;
		}catch (OpenException e) {
			fireException(request, response, e);
			return false;
		}
	}

	@Override
	public void fireException(HttpServletRequest request,
			HttpServletResponse response, OpenException e) {
		throw e;
	}
	
	
	@Override
	public boolean isMatch(HttpServletRequest request,
			HttpServletResponse response,Object handler) {
		return true;
	}

	// 构建接口请求上下文
	protected RequestContext buildApiRequestContext(HttpServletRequest request,
			HttpServletResponse response,Object handler) {

		RequestContext requestContext =  new RequestContext();
		
		initRequestContext(requestContext, request, response, handler);
		
		Map<String,String> paramsMap = this.buildRequestParams(request);
		
		requestContext.setParamsMap(paramsMap);
		requestContext.setRequest(request);
		requestContext.setSession(request.getSession());
		
		String appId = paramsMap.get(requestContext.getAppIdName());
		String sign = paramsMap.get(requestContext.getSignName());
		String timestampStr = paramsMap.get(requestContext.getTimestampName());
		long timestamp = paramsMap.get(requestContext.getTimestampName()) == null 
				? 0L : Long.parseLong(timestampStr);
		
		requestContext.setAppId(appId);
		requestContext.setSign(sign);
		requestContext.setTimestamp(timestamp);
		
		List<String> ignoreParamNames = getIgnoreParamNames(request, response, handler);
		requestContext.setIgnoreParamName(ignoreParamNames);
		
		return requestContext;
	}
	
	protected void initRequestContext(RequestContext requestContext,HttpServletRequest request,
			HttpServletResponse response,Object handler) {
		requestContext.setNeedSign(true);
		requestContext.setAppIdName(APP_ID_NAME);
		requestContext.setSignName(SIGN_NAME);
		requestContext.setTimestampName(TIMESTAMP_NAME);
	}
	
	protected List<String> getIgnoreParamNames(HttpServletRequest request,
			HttpServletResponse response,Object handler) {
		return Arrays.asList(SIGN_NAME);
	}

	// 验证参数
	private void validate(RequestContext requestContext) {
		if(requestContext.isNeedSign()){
			if (!checkAppId(requestContext)) {
				throw new OpenException("appId无效");
			}
	
			// 验证签名
			if (checkSign(requestContext)) {
				// 验证字段
				ValidateHolder validateHolder = ValidateUtil
						.validate(requestContext);
				if (!validateHolder.isSuccess()) { // 验证字段失败
					String errorMsg = buildErrorMsg(validateHolder);
					throw new OpenException(errorMsg);
				}
			} else {
				throw new OpenException("签名无效,sign=" + requestContext.getSign());
			}
		}
	}

	// 构建错误信息
	private String buildErrorMsg(ValidateHolder validateHolder) {
		List<String> errorMsgList = validateHolder.buildValidateErrors();
		StringBuilder msg = new StringBuilder();
		for (String errorMsg : errorMsgList) {
			msg.append(";").append(errorMsg);
		}
		return msg.substring(1).toString();
	}

	// 是否有效appId
	private boolean checkAppId(RequestContext requestContext) {
		String appId = requestContext.getAppId();
		return appSecretManager.isValidAppId(appId);
	}

	// 检查签名
	private boolean checkSign(RequestContext requestContext) {
		if (logger.isDebugEnabled()) {
			logger.debug("===开始验证签名");
		}

		String clientSign = requestContext.getSign();
		String secret = appSecretManager.getSecret(requestContext.getAppId());
		try {
			Map<String, String> paramsMap = requestContext.getAllParams();
			String serverSign = OpenUtil.buildSign(paramsMap, secret,
					requestContext.getIgnoreParamName());
			boolean isTrueSign = serverSign.equals(clientSign);
			logger.info("^^^签名验证结果:" + isTrueSign + ",客户端签名:" + clientSign
					+ ",服务端签名:" + serverSign);

			return isTrueSign;
		} catch (IOException e) {
			logger.error("---构建签名出错.secret=" + secret, e);
			return false;
		}
	}

	protected static String getFullUrl(HttpServletRequest request) {
		return request.getRequestURL() + "?" + request.getQueryString();
	}

	protected Map<String, String> buildRequestParams(HttpServletRequest request) {
		Map paramMap = request.getParameterMap();
		Set keySet = paramMap.keySet();
		Map<String, String> retMap = new HashMap<String, String>(keySet.size());

		for (Object objName : keySet) {
			String paramName = objName.toString();
			String[] values = (String[]) paramMap.get(objName);
			if (values != null && values.length == 1) { // 单值,多值不考虑
				retMap.put(paramName, values[0]);
			}
		}

		return retMap;
	}

	public AppSecretManager getAppSecretManager() {
		return appSecretManager;
	}

	public void setAppSecretManager(AppSecretManager appSecretManager) {
		this.appSecretManager = appSecretManager;
	}
	
}
