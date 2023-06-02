package com.meowoh.restful.interceptor;

import org.springframework.core.Ordered;

/**
 * common definition for interceptor
 *
 * @author songliangliang
 * @since 2023/6/1
 */
@FunctionalInterface
public interface RestfulInterceptor {

    /**
     * accept the request body as a param.
     * u can use this object to do pre/post-interceptor such as do log  ,or monitor ,report or modify the request/response body
     *
     * @param o request/response body
     */
    void accept(Object... o);

    default int order() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
