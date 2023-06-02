package com.meowoh.restful.annotation;

import java.lang.annotation.*;

/**
 * Restful Action scanner
 *
 * @author songliangliang
 * @since 2023/6/1
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestfulScan {

    /**
     * base packages to scan
     *
     * @return base packages
     */
    String[] value() default {};

}
