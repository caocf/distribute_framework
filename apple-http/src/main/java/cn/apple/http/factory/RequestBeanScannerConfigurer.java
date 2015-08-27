package cn.afterturn.http.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import cn.afterturn.http.proxy.RequestProxyHandler;

/**
 * 扫描配置文件
 * 
 * @author JueYue
 * @date 2014年11月15日 下午9:48:31
 */
public class RequestBeanScannerConfigurer implements BeanDefinitionRegistryPostProcessor {
    /**
     * ,; \t\n
     */
    private String basePackage;

    public String getBasePackage() {
        return basePackage;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
                                                                                  throws BeansException {
        registerRequestProxyHandler(registry);
        RequestClassPathMapperScanner scanner = new RequestClassPathMapperScanner(registry);
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage,
            ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
                                                                                   throws BeansException {
    }

    /**
     * RequestProxyHandler 手工注册代理类,减去了用户配置XML的烦恼
     * 
     * @param registry
     */
    private void registerRequestProxyHandler(BeanDefinitionRegistry registry) {
        GenericBeanDefinition requestProxyDefinition = new GenericBeanDefinition();
        requestProxyDefinition.setBeanClass(RequestProxyHandler.class);
        registry.registerBeanDefinition("requestProxyHandler", requestProxyDefinition);
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

}
