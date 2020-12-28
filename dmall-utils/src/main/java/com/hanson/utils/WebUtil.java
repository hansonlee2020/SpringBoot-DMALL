package com.hanson.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: web应用工具
 * @classname: WebUtil
 * @author: Hanson
 * @create: 2020/12/14
 **/
public class WebUtil {

    public static HttpServletRequest getHttpServletRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getRequest();
    }
}
