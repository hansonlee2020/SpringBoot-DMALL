package com.hanson.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: DreamMall
 * @description: 前台系统用户表对应的用户类，实现了比较接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-20 20:24
 **/
@ApiModel(value = "会员对象",description = "会员表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Comparable<Member>, Serializable {
    //用户id
    @ApiModelProperty(name = "memberId",value = "用户ID",dataType = "Integer")
    private Integer memberId;
    //用户名
    @ApiModelProperty(name = "memberName",value = "用户名称",dataType = "String")
    private String memberName;
    //用户密码
    @ApiModelProperty(name = "memberPwd",value = "用户密码",dataType = "String")
    private String memberPwd;
    //加密盐
    @ApiModelProperty(name = "encryptionSalt",value = "加密盐",dataType = "String")
    private String encryptionSalt;
    //性别
    @ApiModelProperty(name = "sex",value = "性别",dataType = "Integer")
    private Integer sex;
    //用户手机
    @ApiModelProperty(name = "mobilePhone",value = "用户手机",dataType = "String")
    private String mobilePhone;
    //用户邮箱
    @ApiModelProperty(name = "email",value = "用户邮箱",dataType = "String")
    private String email;
    //用户地址
    @ApiModelProperty(name = "locationId",value = "用户地址",dataType = "Integer")
    private Integer locationId;
    //用户状态
    @ApiModelProperty(name = "memberState",value = "用户状态",dataType = "Integer")
    private Integer memberState;
    //用户创建日期
    @ApiModelProperty(name = "createTime",value = "用户创建日期",dataType = "Date")
    private Date createTime;

    /**用于用户信息排序，排序规则为用户id首位升序
     * @param o 要比较的用户对象
     * @return 返回结果为正数表示当前对象排靠后，返回结果为负数，表示比较的用户对象排靠后，
     *      * 返回结果为0，表示两对象并排
     */
    @Override
    public int compareTo(Member o) {
        return this.memberId - o.memberId;//默认按照id的首位升序排序
    }
}
