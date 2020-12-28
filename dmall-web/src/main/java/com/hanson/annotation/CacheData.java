package com.hanson.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: 将数据放进缓存或者从缓存中取出数据
 * @annotation: CacheData
 * @author: Hanson
 * @create: 2020/12/14
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheData {
    //缓存的key
    String cacheKey();
    //缓存数据的类型,默认是JSON字符串
    Class<?> dataClass() default String.class;
    //过期时间
    long expireTime() default 30L;
    //过期时间单位
    TimeUnit timeUnit() default TimeUnit.MINUTES;
}
