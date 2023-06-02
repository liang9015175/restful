package com.meowoh.restful.proxy;

import com.meowoh.restful.annotation.RestfulAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Objects;
import java.util.Set;

/**
 * @author songliangliang
 * @since 2023/6/1
 */
@Slf4j
public class RestfulClassScanner extends ClassPathBeanDefinitionScanner {

    private Class<? extends RestfulFactoryBean> factoryBeanClass = RestfulFactoryBean.class;

    public RestfulClassScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public void registerTypeFilter() {
        this.addIncludeFilter((new AnnotationTypeFilter(RestfulAction.class)));
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            log.warn("No RestfulAction was found in '{}' package. Please check your configuration.", basePackages);
        } else {
            this.processBeanDefinition(beanDefinitions);
        }
        return beanDefinitions;
    }

    private void processBeanDefinition(Set<BeanDefinitionHolder> beanDefinitions) {
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitions) {
            log.info("RestfulAction:{}", beanDefinitionHolder.getBeanDefinition().getBeanClassName());

            AbstractBeanDefinition definition = (AbstractBeanDefinition) beanDefinitionHolder.getBeanDefinition();

            String beanClassName = Objects.requireNonNull(definition.getBeanClassName());
            log.debug("Creating RestfulAction with name '{}' and '{}' interface", beanDefinitionHolder.getBeanName(), beanClassName);

            // create bean using bean name but type is factory bean
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            definition.setBeanClass(this.factoryBeanClass);

            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            definition.setScope(ConfigurableBeanFactory.SCOPE_SINGLETON);

        }
    }

    public void setFactoryBeanClass(Class<? extends RestfulFactoryBean> factoryBeanClass) {
        this.factoryBeanClass = factoryBeanClass == null ? RestfulFactoryBean.class : factoryBeanClass;
    }
}
