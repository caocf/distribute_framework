package org.durcframework.open;

public interface Interceptor {
	/** 调用服务前触发 */
	void beforeService(RequestContext requestContext);
	/** 调用服务后触发 */
	void afterService(RequestContext requestContext);
	/** 是否调用 */
	boolean isMatch(RequestContext requestContext);
}
