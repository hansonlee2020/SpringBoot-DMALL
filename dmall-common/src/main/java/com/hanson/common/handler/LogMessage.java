package com.hanson.common.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 日志记录信息
 * @param:
 * @author: Hanson
 * @create: 2020-10-02 15:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogMessage implements Serializable {
    //异常方法全路径
    private String target;
    //方法参数
    private String args;
    //aop类型
    private String kind;
    //异常信息
    private String error;
}
