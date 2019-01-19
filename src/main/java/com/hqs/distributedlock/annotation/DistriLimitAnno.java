package com.hqs.distributedlock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义limit注解
 * @author huangqingshi
 * @Date 2019-01-17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistriLimitAnno {
    public String limitKey() default "limit";
    public int limit() default 1;
}
