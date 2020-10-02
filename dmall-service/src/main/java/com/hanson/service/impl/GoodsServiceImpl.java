package com.hanson.service.impl;


import com.hanson.common.api.CommonResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.constant.ExceptionConstant;
import com.hanson.common.exception.NullCategoryException;
import com.hanson.common.exception.NullSplitPageInfoException;
import com.hanson.mapper.*;
import com.hanson.pojo.dto.GoodsEditTable;
import com.hanson.pojo.dto.GoodsTable;
import com.hanson.pojo.dto.PageBean;
import com.hanson.pojo.vo.Goods;
import com.hanson.pojo.vo.GoodsCategory;
import com.hanson.pojo.vo.MemberOrder;
import com.hanson.service.GoodsService;
import com.hanson.utils.DMStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @program: DreamMall
 * @description: 商品实现类
 * @param:
 * @author: Hanson
 * @create: 2020-03-27 19:30
 **/
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private MemberOrderMapper memberOrderMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Goods> getGoods() {
        return goodsMapper.doGetGoods();
    }

    @Override
    public CommonResult<Integer> addGoods(GoodsEditTable goods) {
        Integer cid = Integer.valueOf(goods.getCateId());//查询对应的分类id
        UUID uuid = UUID.randomUUID();//生成商品id
        String id = DMStringUtils.formatUUID(uuid);//格式id
        //设置商品id
        goods.setId(id);
        Goods item = new Goods(goods,cid);
        Integer result = goodsMapper.doAddGoods(item);
        CommonResult<Integer> res;
        if (result == 1) res = new CommonResult<>(ResultCode.SUCCESS,null, Constant.ADD_GOODS_SUCCESS);
        else res = new CommonResult<>(ResultCode.SUCCESS,null, Constant.ADD_GOODS_FAILED);
        return res;
    }

    @Override
    public List<Goods> getGoodsByName(String name) {
        return goodsMapper.doGetGoodsByName(name);
    }

    @Override
    public GoodsEditTable getGoodsById(String id) {
            Goods goods = goodsMapper.doGetGoodsById(id);//获取商品信息
            Integer categoryId = goods.getCategoryId();//获取商品分类id
            if (categoryId != null) {
                GoodsCategory goodsCategory = goodsCategoryMapper.doGetGoodsCategoryByCategoryId(categoryId);//查询商品分类信息
                if (goodsCategory == null) throw new NullCategoryException(ExceptionConstant.NULL_CATEGORY_EXCEPTION,"");
                else {
                    String cateName = goodsCategory.getCategoryName();
                    if (goods.getImageSrc() == null) goods.setImageSrc("");
                    //商品封装为GoodsEditTable
                    return new GoodsEditTable(goods,cateName);
                }
            }
            return null;

    }

    @Override
    public PageBean<GoodsTable> getGoodsSplit(Integer pageSize, Integer currentPage) {
            Integer startIndex = (currentPage - 1) * pageSize;//分页查询开始索引
            Long total =(long) goodsMapper.doGetCounts();//总数据量
            int pages;
            if (total % pageSize != 0){
                pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
            }else {
                pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
            }
            List<Goods> goodsList = goodsMapper.doGetGoodsSplit(startIndex, pageSize);//分页查询结果集
            if (goodsList.size() <= 0) throw new NullSplitPageInfoException(ExceptionConstant.NULL_PAGE_EXCEPTION,null);//分页信息出错
            Integer size = goodsList.size();//当前页数据量
            //转换商品对象Goods为GoodsTable
            List<GoodsTable> table = new ArrayList<>();
            for (Goods goods : goodsList) {
                String cateName = "";
                Integer categoryId = goods.getCategoryId();//获得商品分类id
                GoodsCategory goodsCategory = goodsCategoryMapper.doGetGoodsCategoryByCategoryId(categoryId);//查询商品分类信息
                if (goodsCategory == null) {
                    throw new NullCategoryException(ExceptionConstant.NULL_CATEGORY_EXCEPTION,null);//分类信息出错
                }else {
                    cateName = goodsCategory.getCategoryName();
                }
                if (goods.getImageSrc() == null) goods.setImageSrc("");
                GoodsTable goodsTable = new GoodsTable(goods, cateName);
                table.add(goodsTable);
            }
            return new PageBean<>(total,currentPage,pageSize,pages,size,table);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> batchRemoveGoods(List<String> ids) {
        Set<String> newIds = new HashSet<>(ids);
        //查询哪些用户订单购买了该商品
        for (String newId : newIds) {
            Set<String> set = new HashSet<>();
            List<MemberOrder> memberOrders = memberOrderMapper.doGetMemberOrderByGoodsId(newId);
            for (MemberOrder memberOrder : memberOrders) {
                memberOrderMapper.doDeleteMemberOrder(memberOrder.getOrderId());
                set.add(memberOrder.getOrderId());
            }
            //删除订单
            orderMapper.doBatchDeleteOrder(set);
        }
        Integer num = goodsMapper.doBatchDeleteGoods(newIds);//执行批量删除
        CommonResult<Integer> result;
        if (num != 0) result = new CommonResult<>(ResultCode.SUCCESS,num,Constant.DELETES_GOODS_SUCCESS);
        else result = new CommonResult<>(ResultCode.FAILED,num,Constant.DELETES_GOODS_FAILED);
        return result;
    }

    @Override
    public CommonResult<Integer> updateGoods(GoodsEditTable editTable) {
        Integer cateId = Integer.valueOf(editTable.getCateId());
        GoodsCategory goodsCategory = goodsCategoryMapper.doGetGoodsCategoryByCategoryId(cateId);//获取商品分类信息
        //将GoodsEditTable转为Goods对象
        Goods goods = new Goods(editTable,goodsCategory.getCategoryId());
        Integer num = goodsMapper.doUpdateGoods(goods);
        if (num == 0) return new CommonResult<>(ResultCode.FAILED,num);
        return new CommonResult<>(ResultCode.SUCCESS,num,Constant.UPDATE_GOODS_SUCCESS);
    }

    @Override
    public CommonResult<Integer> updateGoodsState(String id, Integer state) {
        Integer num = goodsMapper.doUpdateGoodsState(id, state);
        if (num == 1) return new CommonResult<>(ResultCode.SUCCESS, num, Constant.UPDATE_GOODS_SUCCESS);
        else return new CommonResult<>(ResultCode.SUCCESS, num, Constant.UPDATE_GOODS_FAILED);
    }

    @Override
    public PageBean<GoodsTable> getGoodsSplitWithSearch(Integer pageSize, Integer currentPage, String key) {
        String newKey = key.replaceAll(" ", "");
        if ("".equals(newKey)) newKey = null;
        Integer startIndex = (currentPage - 1) * pageSize;//分页查询开始索引
        List<Goods> search = goodsMapper.doGetGoodsWithSearch(newKey);
        if (search.size() <= 0) throw new NullSplitPageInfoException(ExceptionConstant.NULL_PAGE_EXCEPTION,null);//分页信息出错
        Long total =(long) search.size();//模糊查询总数据量
        int pages;
        if (total % pageSize != 0){
            pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
        }else {
            pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
        }
        List<Goods> goodsList = goodsMapper.doGetGoodsSplitWithSearch(startIndex, pageSize,newKey);//分页查询结果集
        Integer size = goodsList.size();//当前页数据量
        //转换商品对象Goods为GoodsTable
        List<GoodsTable> table = new ArrayList<>();
        for (Goods goods : goodsList) {
            String cateName;
            Integer categoryId = goods.getCategoryId();//获得商品分类id
            GoodsCategory goodsCategory = goodsCategoryMapper.doGetGoodsCategoryByCategoryId(categoryId);//根据id查询所在分类
            if (goodsCategory == null) {
                throw new NullCategoryException(ExceptionConstant.NULL_CATEGORY_EXCEPTION,null);//分类信息出错
            }else {
                cateName = goodsCategory.getCategoryName();//获取商品的三级分类名称
            }
            if (goods.getImageSrc() == null) goods.setImageSrc("");
            GoodsTable goodsTable = new GoodsTable(goods, cateName);
            table.add(goodsTable);
        }
        return new PageBean<>(total,currentPage,pageSize,pages,size,table);
    }

    @Override
    public Integer countGoods() {
        return goodsMapper.doGetCounts();
    }

    @Override
    public CommonResult<Integer> batchUpdateGoods(List<String> ids, Integer state) {
        Set<String> newIds = new HashSet<>(ids);
        Integer res = goodsMapper.doBatchUpdateGoods(newIds, state);
        return new CommonResult<>(ResultCode.SUCCESS,res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> deleteGoods(String gid) {
        //查询哪些订单有该商品
        List<MemberOrder> memberOrders = memberOrderMapper.doGetMemberOrderByGoodsId(gid);
        if (memberOrders != null && memberOrders.size() > 0){
            //删除订单
            Set<String> set = new HashSet<>();
            for (MemberOrder memberOrder : memberOrders) {
                set.add(memberOrder.getOrderId());
                //删除用户订单
                memberOrderMapper.doDeleteMemberOrder(memberOrder.getOrderId());
            }
            orderMapper.doBatchDeleteOrder(set);
        }
        Integer num = goodsMapper.doDeleteGoods(gid);
        if (num != 0) return new CommonResult<>(ResultCode.SUCCESS,num,Constant.DELETE_GOODS_SUCCESS);
        return new CommonResult<>(ResultCode.FAILED,num,Constant.DELETE_GOODS_FAILED);
    }


    /*
    * @description: 验证是否整数或小数
    * @params: [s] 需要验证的字符串
    * @return: boolean
    * @Date: 2020/5/9
    */
    public boolean checkNum(String s){
        String regex = "\\d+(.\\d+)?" ;
        if (s.matches(regex)){
            if (s.length() == 1){
                return true;
            }
            if ("0".equals(s.substring(0,1))){
                if (s.contains(".")){
                    if (s.indexOf(".") > 1){
                        return false;
                    }
                    return s.matches(regex);
                }
                return false;
            }
            return s.matches(regex);
        }else {
            return false;
        }
    }

    public boolean checkInteger(String s){
        String regex = "\\d+";
        if (s.matches(regex)){
            if (s.length() == 1){
                return true;
            }
            return !"0".equals(s.substring(0, 1));
        }
        return false;
    }
}
