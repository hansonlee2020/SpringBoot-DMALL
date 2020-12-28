package com.hanson.annotation;

import java.lang.annotation.*;

/**
 * @description: 缓存清理
 * @annotation: CacheClear
 * @author: Hanson
 * @create: 2020/12/14
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheClear {
    //需要清理缓存的key名称数组
    String[] keys();
}
