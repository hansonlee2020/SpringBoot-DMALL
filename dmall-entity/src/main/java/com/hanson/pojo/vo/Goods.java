package com.hanson.pojo.vo;

import com.hanson.pojo.dto.GoodsEditTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @program: DreamMall
 * @description: 商品类，描述一个商品
 * @param:
 * @author: Hanson
 * @create: 2020-03-27 18:55
 **/
@ApiModel(value = "商品对象",description = "商品表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
    //商品id，后台使用UUID类生成，不需要输入
    @ApiModelProperty(name = "productId",value = "商品ID",dataType = "String")
    private String productId;
    //商品名称
    @ApiModelProperty(name = "productName",value = "商品名称",dataType = "String")
    private String productName;
    //商品简介
    @ApiModelProperty(name = "briefInfo",value = "商品简介",dataType = "String")
    private String briefInfo;
    //商品价格
    @ApiModelProperty(name = "price",value = "商品价格",dataType = "Double")
    private Double price;
    //商品库存
    @ApiModelProperty(name = "stock",value = "商品库存",dataType = "Integer")
    private Integer stock;
    //商品限购数量
    @ApiModelProperty(name = "limitNum",value = "商品限购数量",dataType = "Integer")
    private Integer limitNum;
    //商品的分类id
    @ApiModelProperty(name = "categoryId",value = "商品的分类ID",dataType = "Integer")
    private Integer categoryId;
    //商品发布状态
    @ApiModelProperty(name = "releaseState",value = "商品发布状态",dataType = "Integer")
    private Integer releaseState;
    //商品图片路径
    @ApiModelProperty(name = "imageSrc",value = "商品图片路径",dataType = "String")
    private String imageSrc;
    //商品详情
    @ApiModelProperty(name = "productDetails",value = "商品详情",dataType = "String")
    private String productDetails;
    public Goods(GoodsEditTable goods, Integer cid){
        this.productId = goods.getId();
        this.productName = goods.getName();
        this.briefInfo = goods.getBrief();
        this.price = goods.getPrice();
        this.stock = goods.getStock();
        this.limitNum = goods.getLimit();
        this.categoryId = cid;
        this.releaseState = goods.getState();
        this.imageSrc = goods.getImage();
        this.productDetails = goods.getDetails();
    }
}
