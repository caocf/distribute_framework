package org.durcframework.open;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.durcframework.open.annotation.ParamIgnore;

public class OpenUtil {
	private static final String PREFIX_GET = "get";
	private static final String GET_CLASS_NAME = "getClass";
	
	/**
	 * 构建签名
	 * @param paramsMap 参数
	 * @param secret 密钥
	 * @return
	 * @throws IOException
	 */
	public static String buildSign(Map<String, String> paramsMap,String secret,List<String> ignoreParamNames) throws IOException{
		Set<String> keySet = paramsMap.keySet();
		
		List<String> paramNames = new ArrayList<String>(keySet);
		
		Collections.sort(paramNames);
		
		StringBuilder paramNameValue = new StringBuilder();
        
        for (String paramName : paramNames) {
        	if(ignoreParamNames != null && ignoreParamNames.contains(paramName)) {
        		continue;
        	}
        	paramNameValue.append(paramName).append(paramsMap.get(paramName));
		}
        
        String sha1Source = secret + paramNameValue.toString() + secret;
        
		return sha1(sha1Source);
	}
	
	public static Map<String,String> buildParamsMap(Object argu){
		Map<String, String> map = new HashMap<String, String>();
		Method[] methods = argu.getClass().getMethods();
		try {
			for (Method method : methods) {
				if(hasAnnotion(method, ParamIgnore.class)) {
					continue;
				}
				
				String methodName = method.getName();
				if (isGetMethod(methodName)) {
					String paramName = buildParamName(methodName);
					Object value = method.invoke(argu);
					String valueStr = formatValue(value);
					if(valueStr != null) {
						map.put(paramName, valueStr);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private static String formatValue(Object value) {
		if(value == null) {
			return null;
		}
		
		if(value instanceof Collection || value.getClass().isArray()) {
			return null;
		}
		
		return value.toString();
	}
	
	// 是否为get开头的方法
	public static boolean isGetMethod(String methodName){
		return methodName.startsWith(PREFIX_GET) 
				&& !GET_CLASS_NAME.equals(methodName); // 不是getClass()系统方法
	}
	
	/**是否有注解
	 * @param method
	 * @param annotionClass
	 * @return
	 */
	public static boolean hasAnnotion(Method method,Class<?> annotionClass){
		Annotation[] annotations = method.getAnnotations();
		if (annotations == null || annotations.length == 0) {
			return false;
		}
		for (Annotation anno : annotations) {
			String annotionName = annotionClass.getName();
			if (annotionName.equals(anno.annotationType().getName())) {
				return true;
			}
		}
		return false;
	}
	
	
	// 构建列名
	public static String buildParamName(String methodName) {
		return methodName.substring(3, 4).toLowerCase()
				+ methodName.substring(4);

	}
	
	public static String sha1(String data) throws IOException{
		return byte2hex(getSHA1Digest(data));
	}
	
	private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }
	
	/**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
    
    public static void main(String[] args) {
	}
}
