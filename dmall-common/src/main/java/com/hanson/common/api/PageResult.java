package com.hanson.common.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 分页数据封装
 * @param:
 * @author: Hanson
 * @create: 2020-09-08 20:28
 **/
@ApiModel(value = "分页返回结果对象",description = "统一接口分页返回结果")
@Data
@ToString
public class PageResult<R> implements Serializable {
    //处理结果码
    @ApiModelProperty(name = "code",value = "处理结果码",dataType = "long")
    private long code;
    //处理消息
    @ApiModelProperty(name = "msg",value = "处理消息",dataType = "String")
    private String msg;
    //数据总量
    @ApiModelProperty(name = "count",value = "数据总量",dataType = "long")
    private long count;
    //分页数据
    @ApiModelProperty(name = "data",value = "分页数据")
    private R data;

    public PageResult(){
    }

    public PageResult(ResultCode resultCode,long count,R data){
        if (resultCode != null) {
            this.code  = resultCode.getCode();
            this.msg = resultCode.getContent();
        }
        else {
            this.code = -1;//处理代码丢失
            this.msg = "系统错误，处理码丢失";
        }
        if (count < 0) this.count = 0;
        else this.count = count;
        this.data = data;
    }

    public PageResult(long code, String msg, long count, R data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
    public static PageResult pageSuccessResult(){
        return new PageResult(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getContent(),0L,new Object());
    }
}
