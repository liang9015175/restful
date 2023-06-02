package com.meowoh.restful.annotation;

import com.meowoh.restful.interceptor.RestfulInterceptor;
import com.meowoh.restful.interceptor.impl.EmptyRestfulInterceptor;
import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

/**
 * HttpRestful request annotation
 *
 * @author songliangliang
 * @since 2023/6/1
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Restful {

    /**
     * request method
     *
     * @return httpMethod
     */
    HttpMethod method() default HttpMethod.GET;

    /**
     * connect timeout. unit millisecond
     *
     * @return connect timeout
     */
    int connectTimeout() default 3000;


    /**
     * read from the server timeout. unit millisecond
     *
     * @return read timeout
     */
    int readTimeout() default 3000;

    /**
     * request url
     *
     * @return 默认为空
     */
    String url() default "";


    /**
     * multi env config from ur local yml/application file
     * this will added to the request parameters
     *
     * @return env
     */
    String[] env() default "";

    /**
     * pre-interceptor before this request is executed
     *
     * @return pre-interceptor
     */
    Class<? extends RestfulInterceptor>[] preInterceptors() default {EmptyRestfulInterceptor.class};

    /**
     * post interceptor after this request is executed
     *
     * @return post-interceptor
     */
    Class<? extends RestfulInterceptor>[] postInterceptors() default {EmptyRestfulInterceptor.class};

}
