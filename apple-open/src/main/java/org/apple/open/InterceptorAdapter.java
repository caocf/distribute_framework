package org.durcframework.open;

public abstract class InterceptorAdapter implements Interceptor {

	@Override
	public void beforeService(RequestContext requestContext) {
	}

	@Override
	public void afterService(RequestContext requestContext) {
	}

	@Override
	public boolean isMatch(RequestContext apiRequestContext) {
		return true;
	}

}
