package com.hanson.common.annotation;

import java.lang.annotation.*;

/**
 * @Description 分页注解
 * @Author Hanson
 * @Date 2020/11/30 18:02
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Pager {
}
