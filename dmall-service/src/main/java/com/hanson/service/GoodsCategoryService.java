package com.hanson.service;

import com.hanson.common.api.CommonResult;
import com.hanson.pojo.vo.GoodsCategory;

import java.util.List;

/**
 * @program: manager
 * @description: 商品分类接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-21 18:00
 **/
public interface GoodsCategoryService {

    public List<GoodsCategory> getTopGoodsCategories();
    public List<GoodsCategory> getGoodsCategoriesByParentId(Integer parentId);
    public List<GoodsCategory> getGoodsCategories();
    public GoodsCategory getGoodsCategoryByCategoryId(Integer categoryId);
    public GoodsCategory getGoodsCategoryByName(String categoryName);
    public CommonResult<Integer> createGoodsCategory(GoodsCategory goodsCategory);
    public CommonResult<Integer> updateGoodsCategory(GoodsCategory goodsCategory);
    public CommonResult<Integer> deleteGoodsCategory(Integer categoryId, boolean isChecked);
}
