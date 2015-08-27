package cn.afterturn.http.factory;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

import cn.afterturn.http.proxy.RequestProxyHandler;

/**
 * 代理工厂
 * 
 * @author JueYue
 * @date 2014年11月16日 下午4:01:09
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class RequestBeanFactory<T> implements FactoryBean<T> {

    private Class<T>            requestInterface;

    private RequestProxyHandler proxy;

    @Override
    public T getObject() throws Exception {
        return newInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return requestInterface;
    }

    public RequestProxyHandler getProxy() {
        return proxy;
    }

    public Class<T> getRequestInterface() {
        return requestInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private T newInstance() {
        return (T) Proxy.newProxyInstance(requestInterface.getClassLoader(),
            new Class[] { requestInterface }, proxy);
    }

    public void setProxy(RequestProxyHandler proxy) {
        this.proxy = proxy;
    }

    public void setRequestInterface(Class<T> requestInterface) {
        this.requestInterface = requestInterface;
    }

}
