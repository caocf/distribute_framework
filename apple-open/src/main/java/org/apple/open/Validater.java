package org.durcframework.open;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Validater {

	public boolean validate(HttpServletRequest request,
			HttpServletResponse response, Object handler);

	public void fireException(HttpServletRequest request,
			HttpServletResponse response, OpenException e);

	public boolean isMatch(HttpServletRequest request,
			HttpServletResponse response, Object handler);
}
