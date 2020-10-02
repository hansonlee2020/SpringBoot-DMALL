package com.hanson.pojo.dto;

import com.hanson.common.constant.Constant;
import com.hanson.pojo.vo.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * @program: DreamMall
 * @description: 前端用户表类，处理隐私数据后返回用户信息给前端展示
 * @param:
 * @author: Hanson
 * @create: 2020-04-20 21:49
 **/
@ApiModel(value = "会员对象",description = "封装会员信息，返回给前端")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberTable implements Serializable {
    private static ThreadLocal<SimpleDateFormat> THREAD_LOCAL = ThreadLocal.withInitial(SimpleDateFormat::new);
    //用户id
    @ApiModelProperty(name = "mid",value = "用户ID",dataType = "Integer")
    private Integer mid;
    //用户名
    @ApiModelProperty(name = "name",value = "用户名",dataType = "String")
    private String name;
    //性别
    @ApiModelProperty(name = "sex",value = "性别",dataType = "String")
    private String sex;
    //号码
    @ApiModelProperty(name = "phone",value = "号码",dataType = "String")
    private String phone;
    //邮箱
    @ApiModelProperty(name = "email",value = "邮箱",dataType = "String")
    private String email;
    //用户状态
    @ApiModelProperty(name = "state",value = "用户状态",dataType = "String")
    private String state;
    //用户创建日期
    @ApiModelProperty(name = "create_time",value = "用户创建日期",dataType = "String")
    private String create_time;
    public MemberTable(Integer memberId, String memberName, String sex, Integer memberState,String phone,String email, String createTime){
        this.mid = memberId;
        this.name = memberName;
        this.sex = sex;
        if (memberState == Constant.ENABLE_STATE) this.state = Constant.ENABLE;
        else if (memberState == Constant.DISABLE_STATE) this.state = Constant.DISABLE;
        else this.state = Constant.RECYCLE;
        this.create_time = createTime;
        this.phone = phone;
        this.email = email;
    }
    public MemberTable(Member member,String pattern){
        this.mid = member.getMemberId();
        this.name = member.getMemberName();
        if (member.getSex() == Constant.FEMALE_STATE) this.sex = Constant.FEMALE;
        else if (member.getSex() == Constant.MALE_STATE) this.sex = Constant.MALE;
        else this.sex = Constant.SECRECY;
        if (member.getMemberState() != null){
            if (member.getMemberState() == Constant.ENABLE_STATE) this.state = Constant.ENABLE;
            else if (member.getMemberState() == Constant.DISABLE_STATE) this.state = Constant.DISABLE;
            else this.state = Constant.RECYCLE;
        }
        else this.state = Constant.DISABLE;
        this.create_time = setFormatPattern(pattern).format(member.getCreateTime());
        this.phone = member.getMobilePhone();
        this.email = member.getEmail();
    }
    //设置格式
    public SimpleDateFormat setFormatPattern(String pattern){
        SimpleDateFormat dateFormat = THREAD_LOCAL.get();
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }
}
