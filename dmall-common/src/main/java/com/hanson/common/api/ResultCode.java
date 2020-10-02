package com.hanson.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 封装常用数据处理码信息
 * @param:
 * @author: Hanson
 * @create: 2020-09-07 16:20
 **/
public enum  ResultCode implements IResultCode {
    SUCCESS(200,"success","操作成功"),
    FAILED(500, "failed","操作失败"),
    VALIDATE_FAILED(404, "validate_failed","参数检验失败"),
    UNAUTHORIZED(401, "unauthorized","暂未登录或token已经过期"),
    FORBIDDEN(403, "forbidden","没有相关权限"),
    ERROR(-1,"error","系统错误，请联系系统管理员");
    //消息代码
    private long code;
    //消息类型
    private String type;
    //消息内容
    private String content;

    private ResultCode(long code,String type,String content){
        this.code = code;
        this.type = type;
        this.content = content;
    }
    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getContent() {
        return content;
    }
}
