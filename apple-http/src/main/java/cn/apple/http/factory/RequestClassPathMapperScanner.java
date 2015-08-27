package cn.afterturn.http.factory;

import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import cn.afterturn.http.annotation.IRequest;

/**
 * 请求代理扫描类
 * 
 * @author JueYue
 * @date 2014年11月15日 下午9:46:34
 */
public class RequestClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

    public RequestClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
        addIncludeFilter(new AnnotationTypeFilter(IRequest.class));
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            logger.warn("No IRequest mapper was found in '" + Arrays.toString(basePackages)
                        + "' package. Please check your configuration.");
        }
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.getPropertyValues().add("proxy",
                getRegistry().getBeanDefinition("requestProxyHandler"));
            definition.getPropertyValues().add("requestInterface", definition.getBeanClassName());
            definition.setBeanClass(RequestBeanFactory.class);
        }

        return beanDefinitions;
    }

    /**
     * 默认不允许接口的,这里重写,覆盖下
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface()
               && beanDefinition.getMetadata().isIndependent();
    }

}
