package com.hanson.pojo.dto;

import com.hanson.common.constant.Constant;
import com.hanson.pojo.vo.Goods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @program: DreamMall
 * @description: 商品编辑表对象，用于接收需要编辑的商品信息
 * @param:
 * @author: Hanson
 * @create: 2020-04-15 20:06
 **/
@ApiModel(value = "商品表单对象",description = "封装商品所有属性的类，接收前端编辑或添加的商品信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsEditTable implements Serializable {
    //商品id
    @ApiModelProperty(name = "id",value = "商品ID",dataType = "String")
    private String id;
    //商品名称
    @ApiModelProperty(name = "name",value = "商品名称",dataType = "String")
    private String name;
    //商品简介
    @ApiModelProperty(name = "brief",value = "商品简介",dataType = "String")
    private String brief;
    //商品价格
    @ApiModelProperty(name = "price",value = "商品价格",dataType = "Double")
    private Double price;
    //商品库存
    @ApiModelProperty(name = "stock",value = "商品库存",dataType = "Integer")
    private Integer stock;
    //商品限购数量
    @ApiModelProperty(name = "limit",value = "商品限购数量",dataType = "Integer")
    private Integer limit;
    //商品分类id
    @ApiModelProperty(name = "cateId",value = "商品分类id",dataType = "String")
    private String cateId;
    //商品发布状态
    @ApiModelProperty(name = "state",value = "商品发布状态",dataType = "Integer")
    private Integer state;
    //商品图片路径，只保存一张
    @ApiModelProperty(name = "image",value = "商品图片路径",dataType = "String")
    private String image;
    //商品详情
    @ApiModelProperty(name = "details",value = "商品详情",dataType = "String")
    private String details;
    public GoodsEditTable(Goods goods,String cate){
        this.id = goods.getProductId();
        this.name = goods.getProductName();
        this.brief = goods.getBriefInfo();
        this.price = goods.getPrice();
        this.stock = goods.getStock();
        this.limit = goods.getLimitNum();
        this.cateId = cate;
        this.state = goods.getReleaseState();
        this.image = goods.getImageSrc();
        this.details = goods.getProductDetails();
    }
}
