package com.meowoh.restful.proxy;

import com.meowoh.restful.annotation.Restful;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * @author songliangliang
 * @since 2023/6/1
 */
public class RestfulFactoryBean<T> implements FactoryBean<T>, ApplicationContextAware {

    private Class<T> actionInterface;

    private ApplicationContext applicationContext;

    public RestfulFactoryBean(Class<T> actionInterface) {
        this.actionInterface = actionInterface;
    }

    @Override
    public T getObject() throws Exception {
        Assert.notNull(this.actionInterface, "Property 'actionInterface' is required");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.actionInterface);
        enhancer.setCallbacks(new Callback[]{new RestfulMethodInterceptor()}, NoOp.INSTANCE);
        enhancer.setCallbackFilter(method -> {
            int modifiers = method.getModifiers();
            boolean anAbstract = Modifier.isAbstract(modifiers);
            boolean anInterface = Modifier.isInterface(modifiers);
            boolean existedAnnotation = Objects.nonNull(method.getAnnotation(Restful.class));
            if ((anAbstract || anInterface) && existedAnnotation) {
                return 0;
            } else {
                return 1;
            }
        });
        return (T) enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return this.actionInterface;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
