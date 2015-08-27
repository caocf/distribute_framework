package cn.afterturn.http.util;

import cn.afterturn.http.annotation.IRequestMethod;

/**
 * 获取默认注解,减少注解的写入
 * 
 * @author JueYue
 * @date 2014年8月3日 下午1:30:50
 */
public class BaseAnnotation {

    @IRequestMethod(connectTimeout = 60)
    public void requestMethod() {
        throw new UnsupportedOperationException();
    }

}
