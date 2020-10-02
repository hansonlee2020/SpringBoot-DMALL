package com.hanson.common.exception;

import com.hanson.common.constant.ExceptionConstant;

/**
 * @program: manager
 * @description: 获取并设置异常信息，用于处理系统异常后，将错误信息返回给前端
 * @param:
 * @author: Hanson
 * @create: 2020-09-11 15:39
 **/
public class ExceptionMessage {
    private String msg;//异常信息
    private String clazz;//异常所在类
    private String method;//异常所在方法
    private String line;//异常所在行
    private Exception e;

    public ExceptionMessage() {
    }

    public ExceptionMessage(Exception e){
        this(",错误在" + e.getStackTrace()[0],null,null,null,e);
    }
    public ExceptionMessage(String msg, String clazz, String method, String line, Exception e) {
        this.msg = msg;
        this.clazz = clazz;
        this.method = method;
        this.line = line;
        this.e = e;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return msg + ",在" + ExceptionConstant.CLAZZ + clazz
                + ExceptionConstant.METHOD + method + ExceptionConstant.LINE + line;
    }
}
