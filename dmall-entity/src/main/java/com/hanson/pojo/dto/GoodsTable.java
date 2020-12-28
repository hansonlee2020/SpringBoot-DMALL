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
 * @description: 商品表，用于返回商品对象的具体信息，进行商品数据回显展示，实现了序列化接口
 *               和比较接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-12 14:37
 **/
@ApiModel(value = "商品表单对象",description = "封装商品所有属性的类，返回给前端待编辑或浏览的商品信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsTable implements Serializable, Comparable<GoodsTable> {
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
    //商品分类名
    @ApiModelProperty(name = "category",value = "商品分类名",dataType = "String")
    private String category;
    //商品发布状态
    @ApiModelProperty(name = "state",value = "商品发布状态",dataType = "String")
    private String state;     //商品发布状态
    //商品图片路径，只保存一张
    @ApiModelProperty(name = "image",value = "商品图片路径",dataType = "String")
    private String image;
    //商品详情
    @ApiModelProperty(name = "details",value = "商品详情",dataType = "String")
    private String details;

    public GoodsTable(String id, String name, String brief, Double price, Integer stock, Integer limit, String category, Integer state, String image, String details){
        this.id = id;
        this.name = name;
        this.brief = brief;
        this.price = price;
        this.stock = stock;
        this.limit = limit;
        this.category = category;
        if (state == 0){
            this.state = Constant.AUDITING;
        }else if (state ==1){
            this.state = Constant.RELEASED;
        }else if (state == 2){
            this.state = Constant.OFF_SHELF;
        }else this.state = Constant.DEFAULT;
        this.image = image;
        this.details = details;
    }

    public GoodsTable(Goods goods, String category){
        this.id = goods.getProductId();
        this.name = goods.getProductName();
        this.brief = goods.getBriefInfo();
        this.price = goods.getPrice();
        this.stock = goods.getStock();
        this.limit = goods.getLimitNum();
        this.category = category;
        if (goods.getReleaseState() == null) this.state = Constant.DEFAULT;
        else {
            if (goods.getReleaseState() == 0) this.state = Constant.AUDITING;
            else if (goods.getReleaseState() == 1) this.state = Constant.RELEASED;
            else this.state = Constant.OFF_SHELF;
        }
        this.image = goods.getImageSrc();
        this.details = goods.getProductDetails();
    }

    /**进行商品排序，排序规则按照比较商品id的首位，进行升序排序
     * @param o 需要比较的商品对象
     * @return 返回比较结果，结果为负数表示商品对象o排靠后，正数表示商品对象o排靠前，
     * 等于0表示两个商品对象并列，排序结果按照先到的对象排前面，后来的对象排后面
     */
    @Override
    public int compareTo(GoodsTable o) {
        return this.id.charAt(1) - o.id.charAt(1);//默认按照id的首位升序排序
    }
}
