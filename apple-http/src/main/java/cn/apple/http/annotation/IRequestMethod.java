package cn.afterturn.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.afterturn.http.entity.enums.RequestEncodeEnum;
import cn.afterturn.http.entity.enums.RequestResultEnum;
import cn.afterturn.http.entity.enums.RequestTypeEnum;
import cn.afterturn.http.entity.enums.SignTypeEnmu;

/**
 * 求情代理类型
 * Created by jue on 14-4-21.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IRequestMethod {
    /**
     * 连接超时
     * @return
     */
    int connectTimeout() default 60;

    /**
     * 私钥,不参与传递
     * @return
     */
    String defaultKey() default "";

    /**
     * 字符编码
     * @return
     */
    RequestEncodeEnum encode() default RequestEncodeEnum.UTF8;

    /**
     * 是不是默认转码字符编码
     * @return
     */
    boolean isTranscoding() default true;

    /**
     * 返回类型,目前XML 只支持对象类型
     * @return
     */
    RequestResultEnum result() default RequestResultEnum.JSON;

    /**
     * 计算签名方法,默认计算
     * @return
     */
    SignTypeEnmu sign() default SignTypeEnmu.NULL;

    /**
     * 签名名称
     * @return
     */
    String signName() default "sign";

    /**
     * 请求类型
     * @return
     */
    RequestTypeEnum type() default RequestTypeEnum.POST;

    /**
     * 目标URL
     * @return
     */
    String url() default "";
    /**
     * WebServer 内容
     * @return
     */
    String webserver() default "";

}
