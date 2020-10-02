package com.hanson.common.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: manager
 * @description: 该类用于封装返回的数据
 * @param:
 * @author: Hanson
 * @create: 2020-09-07 16:18
 **/
@ApiModel(value = "返回结果对象",description = "统一接口普通返回结果")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<R> {
    //消息代码
    @ApiModelProperty(name = "code",value = "消息代码",dataType = "long")
    private long code;
    //消息类型
    @ApiModelProperty(name = "type",value = "消息类型",dataType = "String")
    private String type;
    //消息内容
    @ApiModelProperty(name = "content",value = "消息内容",dataType = "String")
    private String content;
    //需要返回的数据内容
    @ApiModelProperty(name = "data",value = "需要返回的数据内容")
    private R data;

    public CommonResult(ResultCode resultCode,R data){
        if (resultCode != null) {
            this.code  = resultCode.getCode();
            this.type = resultCode.getType();
            this.content = resultCode.getContent();
        }
        else {
            this.code = -1;//处理代码丢失
            this.type = "error";
            this.content = "系统错误，处理码丢失";
        }
        this.data = data;
    }
    public CommonResult(ResultCode resultCode,R data,String content){
        if (resultCode != null) {
            this.code  = resultCode.getCode();
            this.type = resultCode.getType();
            this.content = content;
        }
        else {
            this.code = -1;//处理代码丢失
            this.type = "error";
            this.content = "系统错误，处理码丢失";
        }
        this.data = data;
    }
    public CommonResult(R data){
        this(ResultCode.SUCCESS,data);
    }
}
