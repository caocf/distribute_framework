package org.durcframework.open.client;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.durcframework.open.OpenUtil;

/**
 * 请求客户端
 * @author hc.tang
 *
 */
public class OpenClient {
	
	private static Logger logger = Logger.getLogger(OpenClient.class);
	
	private String appId;
	private String secret;

	private String appIdName = "appId";
	private String signName = "sign";
	private String timestampName = "timestamp";

	public OpenClient(String appId, String secret) {
		this.appId = appId;
		this.secret = secret;
	}
	
	/**
	 * 发送请求
	 * @param url 请求连接
	 * @param entity 请求参数
	 * @return 服务端返回的内容
	 */
	public String post(String url, Object entity) {
		Map<String, String> paramsMap = OpenUtil.buildParamsMap(entity);
		
		return this.post(url, paramsMap);
	}
	
	/**
	 * 发送请求
	 * @param url 请求连接
	 * @param params 请求参数
	 * @return 服务端返回的内容
	 */
	public String post(String url,Map<String, String> params) {
		params.put(appIdName, this.appId);
		params.put(timestampName, String.valueOf(System.currentTimeMillis()));
		
		String sign = null;
		try {
			sign = OpenUtil.buildSign(params, this.secret,null);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new ClientException("签名构建失败");
		}
		
		params.put(signName, sign);
		
		try {
			return PostUtil.post(url, params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ClientException(e.getMessage());
		}
	}
	

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAppIdName() {
		return appIdName;
	}

	public void setAppIdName(String appIdName) {
		this.appIdName = appIdName;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getTimestampName() {
		return timestampName;
	}

	public void setTimestampName(String timestampName) {
		this.timestampName = timestampName;
	}

}
