package com.meowoh.restful.annotation;

import java.lang.annotation.*;

/**
 * mark this class or interfaces as a restful class
 * and it will scan by the registry .register it to Spring container as a bean
 * restful util will automatically generate a bean with default or customized actions defined by the developer
 *
 * @author songliangliang
 * @since 2023/6/1
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestfulAction {

}
