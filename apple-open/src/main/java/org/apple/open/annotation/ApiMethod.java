package org.durcframework.open.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ApiMethod注解<br>
 * 
 * needSign: 是否启用签名检查,默认true<br>
 * needTimeout: 是否启用超时检查,默认true<br>
 * timeout: 超时时间,毫秒级,默认两分钟(120 * 1000)<br>
 * signName: 接收签名串参数名,默认为sign<br>
 * appIdName: 接收应用ID参数名,默认为appId <br>
 * timestampName: 接收时间戳参数名,默认为timestamp<br>
 * @author hc.tang
 *
 */
@Documented
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ApiMethod {
	/** 是否启用签名检查,默认检查 */
	boolean needSign() default true;
	/** 是否启用超时检查,默认检查 */
	boolean needTimeout() default true;
	/** 设置超时时间,毫秒级,默认两分钟(120 * 1000),此功能尚未实现 */
	int timeout() default 120 * 1000;
	/** 接收签名串参数名,默认为sign */
	String signName() default "sign";
	/** 接收应用ID参数名,默认为appId */
	String appIdName() default "appId";
	/** 接收时间戳参数名,默认为timestamp */
	String timestampName() default "timestamp";
	/** 忽略参数列表,列表中的参数不被加入到签名算法中 */
	String[] ignoreParamNames() default {};
}
