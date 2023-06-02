package com.meowoh.restful.interceptor.impl;

import com.meowoh.restful.interceptor.RestfulInterceptor;

/**
 * default empty implement to @see RestfulInterceptor
 *
 * @author songliangliang
 * @since 2023/6/1
 */
public class EmptyRestfulInterceptor implements RestfulInterceptor {

    @Override
    public void accept(Object... o) {
        // nothing to do
    }
}
