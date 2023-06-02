package com.meowoh.restful.annotation;

import java.lang.annotation.*;

/**
 * mark this class as interceptor  and it will be registered to Spring container as a bean
 *
 * @author songliangliang
 * @since 2023/6/2
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestfulInterceptor {

}
