package org.durcframework.open;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 调用接口上下文
 * @author hc.tang
 *
 */
public class RequestContext {
	
	private String appId; // 接入方id
	private String sign; // 数据签名
	private long timestamp; // 时间戳
	private String methodName; // 方法名称
	private HttpServletRequest request;
	private HttpSession session;
	private long serverStartTime = System.currentTimeMillis();
	private long serverEndTime;
	private boolean needSign = true; // 是否需要检查签名
	
	private String appIdName;
	private String signName;
	private String timestampName;

	private Map<String, String> paramsMap = new HashMap<String, String>();
	
	private List<String> ignoreParamName; 
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public long getApiExecTime() {
		return serverEndTime - serverStartTime;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getServerStartTime() {
		return serverStartTime;
	}

	public void setServerStartTime(long serverStartTime) {
		this.serverStartTime = serverStartTime;
	}

	public long getServerEndTime() {
		return serverEndTime;
	}

	public void setServerEndTime(long serverEndTime) {
		this.serverEndTime = serverEndTime;
	}

	public void setParamsMap(Map<String, String> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public Object getParamValue(String paramName) {
		return paramsMap.get(paramName);
	}

	public Map<String, String> getAllParams() {
		return paramsMap;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	/**
	 * 获取某个参数
	 * @param name
	 * @return
	 */
	public Object getParam(String name) {
		return this.paramsMap.get(name);
	}

	/**
	 * 获取客户端的IP
	 * 
	 * @return
	 */
	public String getIp() {
		return getRealIPAddress(request);
	}
	
	public String getRequestURL(){
		return this.request.getRequestURL().toString();
	}
	
	public boolean isNeedSign() {
		return needSign;
	}

	public void setNeedSign(boolean needSign) {
		this.needSign = needSign;
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

	public List<String> getIgnoreParamName() {
		return ignoreParamName;
	}

	public void setIgnoreParamName(List<String> ignoreParamName) {
		this.ignoreParamName = ignoreParamName;
	}

	/**
	 * 获取客户端的真实地址，支持代理服务器
	 * @author bobdes
	 * @param request
	 * @return
	 */
	private static String getRealIPAddress(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		if (ip.split(",").length > 1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}



}
