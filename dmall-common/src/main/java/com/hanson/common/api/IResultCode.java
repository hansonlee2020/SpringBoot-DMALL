package com.hanson.common.api;

/**
 * @program: manager
 * @description: API的处理码接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-07 17:15
 **/
public interface IResultCode {
    public long getCode();
    public String getType();
    public String getContent();
}
