package com.hanson.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @program: DreamMall
 * @description: 返回信息类，用于包装处理各种请求访问服务器后返回执行结果的提示信息处理
 * @param:
 * @author: Hanson
 * @create: 2020-04-05 19:22
 **/
@ApiModel(value = "消息对象",description = "封装处理结果消息信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    //消息的标题
    @ApiModelProperty(name = "title",value = "消息的标题",dataType = "String")
    private String title;
    //消息的内容
    @ApiModelProperty(name = "content",value = "消息的内容",dataType = "String")
    private String content;
    //消息的类型
    @ApiModelProperty(name = "type",value = "消息的类型",dataType = "String")
    private String type;
}
