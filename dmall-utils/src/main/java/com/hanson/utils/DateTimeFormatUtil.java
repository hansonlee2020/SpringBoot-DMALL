package com.hanson.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * @program: manager
 * @description: 日期时间格式工具
 * @param:
 * @author: Hanson
 * @create: 2020-09-25 09:10
 **/
public class DateTimeFormatUtil {
    private final static ThreadLocal<SimpleDateFormat> THREAD_LOCAL = ThreadLocal.withInitial(SimpleDateFormat::new);

    public static SimpleDateFormat setFormatPattern(String pattern){
        SimpleDateFormat sdf = THREAD_LOCAL.get();
        sdf.applyPattern(pattern);
        return sdf;
    }
}
