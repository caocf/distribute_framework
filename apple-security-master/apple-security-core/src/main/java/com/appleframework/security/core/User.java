package com.appleframework.security.core;

/**
 * @author Cruise.Xu
 */
public interface User {

	Long getId();
	
	String getUsername();
	
	/**
     * 获取属性
     * @param name
     * @return
     */
    Object getAttribute(String name);

}
