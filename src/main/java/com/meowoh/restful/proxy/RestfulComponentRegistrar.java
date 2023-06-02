package com.meowoh.restful.proxy;

import com.meowoh.restful.annotation.RestfulScan;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

/**
 * @author songliangliang
 * @since 2023/6/1
 */
public class RestfulComponentRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String[] packages = Objects.requireNonNull(AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(RestfulScan.class.getName()))).getStringArray("value");
        RestfulClassScanner scanner = new RestfulClassScanner(registry);
        scanner.registerTypeFilter();
        for (String actionPackage : packages) {
            scanner.scan(actionPackage);
        }
    }


    class RestfulDefinitionHolder extends BeanDefinitionHolder {

        public RestfulDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
            super(beanDefinition, beanName);
        }

    }


}
