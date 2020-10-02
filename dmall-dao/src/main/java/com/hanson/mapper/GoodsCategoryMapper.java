package com.hanson.mapper;

import com.hanson.pojo.vo.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: manager
 * @description: 商品分类接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-21 17:18
 **/
@Mapper
@Repository
public interface GoodsCategoryMapper {

    /*
    * @description: 获取所有顶级商品分类
    * @params: []
    * @return: java.util.List<com.hanson.pojo.vo.GoodsCategory>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<GoodsCategory> doGetTopGoodsCategories();

    /*
    * @description:  查询所有商品分类
    * @params: []
    * @return: java.util.List<com.hanson.pojo.vo.GoodsCategory>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<GoodsCategory> doGetGoodsCategories();

    /*
    * @description: 根据分类父ID获取部分分类信息
    * @params: [parentId] 父分类ID
    * @return: java.util.List<com.hanson.pojo.vo.GoodsCategory>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<GoodsCategory> doGetGoodsCategoriesByParentId(@Param("pid") Integer parentId);


    /*
    * @description: 根据分类ID获取商品分类信息
    * @params: [categoryId] 分类ID
    * @return: com.hanson.pojo.vo.GoodsCategory
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public GoodsCategory doGetGoodsCategoryByCategoryId(@Param("cid") Integer categoryId);

    /*
    * @description: 根据分类名称获取商品分类信息
    * @params: [categoryName]
    * @return: com.hanson.pojo.vo.GoodsCategory
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public GoodsCategory doGetGoodsCategoryByName(@Param("name") String categoryName);


    /*
    * @description: 创建商品分类
    * @params: [goodsCategory] 商品分类对象
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doCreateGoodsCategory(@Param("gcate") GoodsCategory goodsCategory);

    /*
    * @description: 更新商品分类
    * @params: [goodsCategory] 商品分类对象
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doUpdateGoodsCategory(@Param("gcate")GoodsCategory goodsCategory);


    /*
    * @description: 根据分类ID删除商品分类
    * @params: [categoryId] 分类ID
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doDeleteGoodsCategory(@Param("cid") Integer categoryId);
}
