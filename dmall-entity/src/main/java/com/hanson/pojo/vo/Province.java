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
 * @description: 省份表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 20:52
 **/
@ApiModel(value = "省份对象",description = "省份表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Province implements Serializable {
    //主键id
    @ApiModelProperty(name = "id",value = "主键ID",dataType = "Integer")
    private Integer id;
    //省份id
    @ApiModelProperty(name = "provinceId",value = "省份ID",dataType = "String")
    private String provinceId;
    //省份名称
    @ApiModelProperty(name = "provinceName",value = "省份名称",dataType = "String")
    private String provinceName;
}
