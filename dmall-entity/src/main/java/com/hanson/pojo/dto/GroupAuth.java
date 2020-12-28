package com.hanson.pojo.dto;

import com.hanson.pojo.vo.AuthGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: manager
 * @description: 权限组-权限
 * @param:
 * @author: Hanson
 * @create: 2020-09-17 18:50
 **/
@ApiModel(value = "权限组-权限对象",description = "封装权限组以及拥有的权限，返回给前端用于编辑或添加的权限信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupAuth<T> implements Serializable {
    //权限组id
    @ApiModelProperty(name = "gid",value = "权限组ID",dataType = "Integer")
    private Integer gid;
    //权限组名称
    @ApiModelProperty(name = "gname",value = "权限组名称",dataType = "String")
    private String gname;
    //权限组拥有的权限
    @ApiModelProperty(name = "auths",value = "该权限组的权限列表",dataType = "List")
    private List<T> auths;
    public GroupAuth(AuthGroup authGroup,List<T> auths){
        this.gid = authGroup.getGroupId();
        this.gname = authGroup.getGroupName();
        this.auths = auths;
    }
}
