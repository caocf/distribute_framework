package cn.afterturn.http.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Request 请求参数注解
 * 
 * @author JueYue 2014年11月16日
 * @version 1.1
 * @see 替代了RequestParams 因为RequestParams 和spring的命名有冲突,所以以后所有的注解都是已IRequest开头
 */
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IRequestParam {
    /**
     * 是否参会签名计算
     * 
     * @return
     */
    boolean isSign() default true;

    /**
     * 顺序 for rest OR signCal 2014年5月15日
     * 
     * @return
     */
    int order() default 0;

    /**
     * 参数名称 2014年5月15日
     * 
     * @return
     */
    String value();
}
