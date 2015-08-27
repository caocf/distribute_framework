package org.durcframework.open.support;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.durcframework.open.Interceptor;
import org.durcframework.open.OpenValidater;
import org.durcframework.open.RequestContext;
import org.durcframework.open.annotation.ApiMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 基于Spring拦截器,配置参见springInterceptorConfig.xml
 * @author hc.tang
 *
 */
public class SpringOpenInterceptor extends OpenValidater implements HandlerInterceptor {
	
	private static final String REQUEST_CONTEXT_NAME = "REQUEST_CONTEXT_NAME";

	private Logger logger = Logger.getLogger(getClass());

	private List<Interceptor> processors = new ArrayList<Interceptor>();

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		boolean ret = this.validate(request, response, handlerMethod);
		
		if(ret) {
			RequestContext requestContext = (RequestContext)request.getAttribute(REQUEST_CONTEXT_NAME);
			
			this.execBeforeProcessors(requestContext);
		}
		
		return ret;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		RequestContext requestContext = (RequestContext)request.getAttribute(REQUEST_CONTEXT_NAME);
		
		try{
			execAfterProcessors(requestContext);
		}catch (Exception e) {}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		RequestContext requestContext = (RequestContext)request.getAttribute(REQUEST_CONTEXT_NAME);
		requestContext.setServerEndTime(System.currentTimeMillis());
		logger.info("^^^执行接口服务结束,耗时:" + requestContext.getApiExecTime() + "毫秒");
	}
	
	@Override
	protected void initRequestContext(RequestContext requestContext,
			HttpServletRequest request, HttpServletResponse response,
			Object handler) {
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		ApiMethod apiMethod = handlerMethod.getMethodAnnotation(ApiMethod.class);
		
		requestContext.setNeedSign(apiMethod.needSign());
		requestContext.setMethodName(handlerMethod.getMethod().getName());
		requestContext.setAppIdName(apiMethod.appIdName());
		requestContext.setSignName(apiMethod.signName());
		requestContext.setTimestampName(apiMethod.timestampName());
		
		request.setAttribute(REQUEST_CONTEXT_NAME, requestContext);
	}

	@Override
	protected List<String> getIgnoreParamNames(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		ApiMethod apiMethod = handlerMethod.getMethodAnnotation(ApiMethod.class);
		
		List<String> ignoreParamNames = new ArrayList<String>();
		
		ignoreParamNames.add(apiMethod.signName());
		
		String[] paramNames = apiMethod.ignoreParamNames();
		
		for (String paramName : paramNames) {
			ignoreParamNames.add(paramName);
		}
		
		return ignoreParamNames;
	}
	
	@Override
	public boolean isMatch(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		return handlerMethod.getMethodAnnotation(ApiMethod.class) != null;
	}
	
	
	private void execBeforeProcessors(RequestContext requestContext){
		for (Interceptor apiProcessor : processors) {
			if(apiProcessor.isMatch(requestContext)){
				apiProcessor.beforeService(requestContext);
			}
		}
	}
	
	private void execAfterProcessors(RequestContext requestContext){
		for (Interceptor apiProcessor : processors) {
			if(apiProcessor.isMatch(requestContext)){
				apiProcessor.afterService(requestContext);
			}
		}
	}

	public void setProcessors(List<Interceptor> processors) {
		this.processors = processors;
	}

}
