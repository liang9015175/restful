package com.meowoh.restful.proxy;

import com.meowoh.restful.annotation.Restful;
import com.meowoh.restful.interceptor.RestfulInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.propertyeditors.URLEditor;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author songliangliang
 * @since 2023/6/2
 */
public class RestfulMethodInterceptor implements MethodInterceptor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Restful annotation = method.getAnnotation(Restful.class);
        Assert.notNull(annotation, "annotation must not be null");

        String url = annotation.url();

        HttpMethod httpMethod = annotation.method();


        String[] env = annotation.env(); // {"key:v","key:v"}

        List<? extends RestfulInterceptor> preInterceptors = Arrays.stream(annotation.preInterceptors()).map(v -> applicationContext.getBean(v)).sorted().collect(Collectors.toList());
        List<? extends RestfulInterceptor> postInterceptors = Arrays.stream(annotation.postInterceptors()).map(v -> applicationContext.getBean(v)).sorted().collect(Collectors.toList());

        Map<String, Object> mergedRequestBody = new HashMap<>();

        Map<String, Object> mergedRequestParam = new HashMap<>();

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(RequestBody.class)) {
                BeanMap.create(objects[i]).forEach((k, v) -> {
                    mergedRequestBody.put(k.toString(), v);
                });
            }
            if (parameter.isAnnotationPresent(RequestParam.class)) {
                mergedRequestParam.put(parameter.getName(), objects[i]);
            }
        }
        // parse extra env which used as request parameters
        Arrays.stream(env).map(v -> v.split(":")).forEach(v -> {
            String key = v[0];
            String value = v[1];
            String resolvedValue = this.applicationContext.getEnvironment().resolvePlaceholders(value);
            mergedRequestParam.put(key, resolvedValue);
        });

        // build url
        RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);

        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
