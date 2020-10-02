package com.hanson.pojo.vo;

import com.hanson.common.constant.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @program: DreamMall
 * @description: 定义一个用户类，用于后台系统用户登陆和新增用户操作
 * @param:
 * @author: Hanson
 * @create: 2020-03-19 11:58
 **/
@ApiModel(value = "管理员对象",description = "管理员表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    //用户ID
    @ApiModelProperty(name = "userId",value = "用户ID",dataType = "String")
    private String userId;
    //用户名
    @ApiModelProperty(name = "userName",value = "用户名称",dataType = "String")
    private String userName;
    //用户密码
    @ApiModelProperty(name = "userPwd",value = "用户密码",dataType = "String")
    private String userPwd;
    //用户状态
    @ApiModelProperty(name = "userState",value = "用户状态",dataType = "Integer")
    private Integer userState;
    //加密盐值
    @ApiModelProperty(name = "encryptionSalt",value = "加密盐值",dataType = "String")
    private String encryptionSalt;

    public User(String userName, String userPwd){
        this("0",userName,userPwd, Constant.ENABLE_STATE,"");
    }

    public User(String userName, String userPwd, String encryptionSalt){
        this("0",userName,userPwd,Constant.ENABLE_STATE,encryptionSalt);
    }
}
