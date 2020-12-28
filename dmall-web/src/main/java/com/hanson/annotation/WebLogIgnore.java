package com.hanson.annotation;

import java.lang.annotation.*;

/**
 * @description: 忽略请求日志记录
 * @annotation: WebLogIgnore
 * @author: Hanson
 * @create: 2020/12/17
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface WebLogIgnore {
}
