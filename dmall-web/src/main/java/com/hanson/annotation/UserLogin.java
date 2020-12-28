package com.hanson.annotation;

import java.lang.annotation.*;

/**
 * @Description 用户登录
 * @Author Hanson
 * @Date 2020/11/26 16:30
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface UserLogin {
}
