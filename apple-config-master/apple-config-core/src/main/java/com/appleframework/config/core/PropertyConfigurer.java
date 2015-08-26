package com.appleframework.config.core;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.appleframework.config.core.util.ObjectUtils;

public class PropertyConfigurer {
	
	private static Logger logger = Logger.getLogger(PropertyConfigurer.class);
	
	public static Properties props = null;
	
	public static void load(StringReader reader){
		if(null == props)
			props = new Properties();
		try {
			props.load(reader);
		} catch (IOException e) {			
			logger.error(e);
		}
	}
	
	public static void load(Properties defaultProps){
		if(null == props) {
			props = new Properties();
			convertProperties(defaultProps);
		}
		else {
			convertProperties(defaultProps);
		}
	}
	
	/**
	 * Convert the given merged properties, converting property values
	 * if necessary. The result will then be processed.
	 * <p>The default implementation will invoke {@link #convertPropertyValue}
	 * for each property value, replacing the original with the converted value.
	 * @param defaultProps the Properties to convert
	 * @see #processProperties
	 */
	public static void convertProperties(Properties defaultProps) {
		Enumeration<?> propertyNames = defaultProps.propertyNames();
		while (propertyNames.hasMoreElements()) {
			String propertyName = (String) propertyNames.nextElement();
			String propertyValue = defaultProps.getProperty(propertyName);
			if (ObjectUtils.isNotEmpty(propertyName)) {
				props.setProperty(propertyName, propertyValue);
			}
		}
	}

	public static Object getProperty(String key) {
		if(null == props)
			return null;
		else
			return props.get(key);
	}
	
	public static String getValue(String key) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在Diamond中添加或设置的内容为空");
			return null;
		}
	}
	
	public static String getValue(String key, String defaultValue) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在Diamond中添加或设置的内容为空");
			return defaultValue;
		}
	}
	
	public static String getString(String key) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在Diamond中添加或设置的内容为空");
			return null;
		}
	}
	
	public static String getString(String key, String defaultString) {
		Object object = getProperty(key);
		if(null != object) {
			return (String)object;
		}
		else {
			logger.warn("配置项为" + key + "的配置未在Diamond中添加或设置的内容为空");
			return defaultString;
		}
	}
	
	public static Long getLong(String key) {
		Object object = getProperty(key);
		if(null != object)
			return Long.parseLong(object.toString());
		else {
			logger.warn("配置项为" + key + "的配置未在Diamond中添加或设置的内容为空");
			return null;
		}
	}
	
	public static Long getLong(String key, long defaultLong) {
		Object object = getProperty(key);
		if(null != object)
			return Long.parseLong(object.toString());
		else {
			logger.warn("配置项为" + key + "的配置未在Diamond中添加或设置的内容为空");
			return defaultLong;
		}
	}
	
	public static Integer getInteger(String key) {
		Object object = getProperty(key);
		if(null != object) {
			return Integer.parseInt(object.toString());
		}
		else {
			logger.warn("配置项为" + key + "的配置未在Diamond中添加或设置的内容为空");
			return null;
		}
	}
	
	public static Integer getInteger(String key, int defaultInt) {
		Object object = getProperty(key);
		if(null != object) {
			return Integer.parseInt(object.toString());
		}
		else {
			logger.warn("配置项为" + key + "的配置未在Diamond中添加或设置的内容为空");
			return defaultInt;
		}
	}
	
	public static String getString(String key, Object[] array) {
		String message = getValue(key);
		if(null != message) {
			return MessageFormat.format(message, array);  
		}
		else {
			return null;
		}
	}
	
	public static String getValue(String key, Object... array) {
		String message = getValue(key);
		if(null != message) {
			return MessageFormat.format(message, array);  
		}
		else {
			return null;
		}
	}	
}