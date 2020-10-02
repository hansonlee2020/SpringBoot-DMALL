package com.hanson.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @program: DreamMall
 * @description: 区表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 21:08
 **/
@ApiModel(value = "地区对象",description = "地区表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area implements Serializable {
    //主键id
    @ApiModelProperty(name = "id",value = "主键ID",dataType = "Integer")
    private Integer id;
    //区id
    @ApiModelProperty(name = "areaId",value = "地区ID",dataType = "String")
    private String areaId;
    //区名
    @ApiModelProperty(name = "areaName",value = "地区名称",dataType = "String")
    private String areaName;
    //区所属城市id
    @ApiModelProperty(name = "cityId",value = "地区所属城市ID",dataType = "String")
    private String cityId;
}
