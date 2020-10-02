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
 * @description: 市表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 21:07
 **/
@ApiModel(value = "城市对象",description = "城市表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {
    //主键id
    @ApiModelProperty(name = "id",value = "主键ID",dataType = "Integer")
    private Integer id;
    //城市id
    @ApiModelProperty(name = "cityId",value = "城市ID",dataType = "String")
    private String cityId;
    //城市名称
    @ApiModelProperty(name = "cityName",value = "城市名称",dataType = "String")
    private String cityName;
    //城市所属省份id
    @ApiModelProperty(name = "provinceId",value = "城市所属省份ID",dataType = "String")
    private String provinceId;
}
