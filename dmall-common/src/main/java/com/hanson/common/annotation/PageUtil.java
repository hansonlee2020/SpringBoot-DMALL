package com.hanson.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Description 分页处理注解
 * @Author Hanson
 * @Date 2020/11/30 18:21
 **/
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PageUtil {

    int start() default 1;
    int size() default 10;
}
