package com.hanson.pojo.vo;

import com.hanson.common.constant.Constant;
import com.hanson.utils.DateTimeFormatUtil;
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
 * @description: 订单物流信息表
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 13:21
 **/
@ApiModel(value = "物流记录对象",description = "物流记录表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsRecord implements Serializable {
    //主键id
    @ApiModelProperty(name = "id",value = "主键ID",dataType = "Integer")
    private Integer id;
    //物流单号id
    @ApiModelProperty(name = "recordId",value = "物流单号ID",dataType = "long")
    private Long recordId;
    //物流公司id
    @ApiModelProperty(name = "logisticsId",value = "物流公司ID",dataType = "Integer")
    private Integer logisticsId;
    //创建时间
    @ApiModelProperty(name = "createTime",value = "创建时间",dataType = "Date")
    private Date createTime;

}
