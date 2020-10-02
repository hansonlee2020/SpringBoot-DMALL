package com.hanson.service.impl;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.exception.DmallException;
import com.hanson.mapper.GoodsCategoryMapper;
import com.hanson.mapper.GoodsMapper;
import com.hanson.pojo.vo.Goods;
import com.hanson.pojo.vo.GoodsCategory;
import com.hanson.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @program: manager
 * @description:
 * @param:
 * @author: Hanson
 * @create: 2020-09-21 18:06
 **/
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsCategory> getTopGoodsCategories() {
        return goodsCategoryMapper.doGetTopGoodsCategories();
    }

    @Override
    public List<GoodsCategory> getGoodsCategoriesByParentId(Integer parentId) {
        return goodsCategoryMapper.doGetGoodsCategoriesByParentId(parentId);
    }

    @Override
    public List<GoodsCategory> getGoodsCategories() {
        //获取所有分类
        List<GoodsCategory> categories = goodsCategoryMapper.doGetGoodsCategories();
        if (categories == null) throw new NullPointerException("没有任何分类数据");
        return categories;
    }

    @Override
    public GoodsCategory getGoodsCategoryByCategoryId(Integer categoryId) {
        return goodsCategoryMapper.doGetGoodsCategoryByCategoryId(categoryId);
    }

    @Override
    public GoodsCategory getGoodsCategoryByName(String categoryName) {
        return goodsCategoryMapper.doGetGoodsCategoryByName(categoryName);
    }


    @Override
    public CommonResult<Integer> createGoodsCategory(GoodsCategory goodsCategory) {
        //检查是否存在分类了
        GoodsCategory category = goodsCategoryMapper.doGetGoodsCategoryByName(goodsCategory.getCategoryName());
        if (category != null) throw new DmallException("分类已存在，不可重复添加！");
        Integer cnum = goodsCategoryMapper.doCreateGoodsCategory(goodsCategory);
        if (cnum > 0) return new CommonResult<>(ResultCode.SUCCESS,cnum,"已新增分类：" + goodsCategory.getCategoryName());
        return new CommonResult<>(ResultCode.FAILED,null,"无法新增分类！");
    }

    @Override
    public CommonResult<Integer> updateGoodsCategory(GoodsCategory goodsCategory) {
        Integer unum = goodsCategoryMapper.doUpdateGoodsCategory(goodsCategory);
        if (unum > 0) return new CommonResult<>(ResultCode.SUCCESS,unum,"已修改分类");
        return new CommonResult<>(ResultCode.FAILED,null,"无法修改分类！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> deleteGoodsCategory(Integer categoryId, boolean isChecked) {
        //检查分类是否被使用
        List<Goods> goods = goodsMapper.doGetGoodsByCategoryId(categoryId);
        //返回询问是否继续删除
        if (!isChecked && goods != null && goods.size() > 0){
            //数据过多集中处理
            String msg = "";
            msg = "统计发现：有" + goods.size() + " 个商品正在使用该分类，是否将商品分类恢复为随机分类，继续删除该分类？";
            return new CommonResult<>(ResultCode.FAILED,goods.size(),msg);
        }
        //统计
        int count = 0;
        //确认继续删除
        if (goods != null){
            //随机顶级分类
            List<GoodsCategory> topCates = goodsCategoryMapper.doGetTopGoodsCategories();
            if (topCates == null) throw new NullPointerException("没有顶级分类数据");
            GoodsCategory category = topCates.get(new Random().nextInt(topCates.size()));
            //重置商品分类
            for (Goods good : goods) {
                good.setCategoryId(category.getCategoryId());
                Integer unum = goodsMapper.doUpdateGoods(good);
                if (unum <= 0 ) throw new DmallException("重置商品分类失败");
                count ++;
            }
        }
        //删除分类
        Integer dnum = goodsCategoryMapper.doDeleteGoodsCategory(categoryId);
        if (dnum <= 0) throw new DmallException("删除分类失败");
        return new CommonResult<>(ResultCode.SUCCESS,dnum);
    }
}
