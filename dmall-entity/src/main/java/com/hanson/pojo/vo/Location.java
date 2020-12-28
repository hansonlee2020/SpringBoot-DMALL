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
 * @description: 位置信息表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 19:53
 **/
@ApiModel(value = "位置对象",description = "位置表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location implements Serializable {
    //主键id
    @ApiModelProperty(name = "id",value = "主键ID",dataType = "Integer")
    private Integer id;
    //位置id
    @ApiModelProperty(name = "locationId",value = "位置ID",dataType = "Integer")
    private Integer locationId;
    //省份id
    @ApiModelProperty(name = "provinceId",value = "省份ID",dataType = "String")
    private String provinceId;
    //城市id
    @ApiModelProperty(name = "cityId",value = "城市ID",dataType = "String")
    private String cityId;
    //  区/县id
    @ApiModelProperty(name = "areaId",value = "区/县ID",dataType = "String")
    private String areaId;
    //详细地址，如xxx镇xxx村或者xxx街道xxx号
    @ApiModelProperty(name = "address",value = "详细地址",dataType = "String")
    private String address;
}
