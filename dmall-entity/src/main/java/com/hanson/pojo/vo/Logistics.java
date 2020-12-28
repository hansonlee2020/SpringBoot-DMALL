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
 * @description: 物流公司表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 12:32
 **/
@ApiModel(value = "物流对象",description = "物流表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logistics implements Serializable {
    //物流公司id
    @ApiModelProperty(name = "id",value = "物流公司ID",dataType = "Integer")
    private Integer id;
    //物流公司名称
    @ApiModelProperty(name = "logisticsName",value = "物流公司名称",dataType = "String")
    private String logisticsName;
    //排序值
    @ApiModelProperty(name = "sort",value = "排序值",dataType = "Integer")
    private Integer sort;
    //启用状态
    @ApiModelProperty(name = "state",value = "启用状态",dataType = "Integer")
    private Integer state;
}
