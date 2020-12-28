package com.hanson.service;


import com.hanson.common.api.CommonResult;
import com.hanson.pojo.dto.GoodsEditTable;
import com.hanson.pojo.dto.GoodsTable;
import com.hanson.pojo.dto.PageBean;
import com.hanson.pojo.vo.Goods;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 商品service层接口
 * @param:
 * @author: Hanson
 * @create: 2020-03-27 19:28
 **/
public interface GoodsService {
    /*
    * @description: 获取全部商品
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Goods> 返回商品集合，集合对象类型为Goods
    * @Date: 2020/3/27
    */
    public List<Goods> getGoods();


    /*
    * @description: 添加新商品
    * @params: [data] 商品的处理信息
    * @return: com.hanson.dto.Message 添加成功返回处理成功信息，否则返回异常信息
    * @Date: 2020/4/7
    */
    public CommonResult<Integer> addGoods(GoodsEditTable goods);


    /*
    * @description: 根据商品名称查询商品，用于商品相似度匹配
    * @params: [name] 要查询的商品名称
    * @return: java.util.List<com.hanson.pojo.Goods> 返回相同商品名的商品对象，list集合
    * @Date: 2020/4/8
    */
    public List<Goods> getGoodsByName(String name);



    /*
     * @description: 根据商品id查询商品信息
     * @params: [id]
     * @return: com.hanson.dto.GoodsEditTable
     * @throws: Exception
     * @Date: 2020/4/15
     */
    public GoodsEditTable getGoodsById(String id);



    /*
    * @description: 根据每页显示数量和当前页分页查询商品
    * @params: [pageSize, currentPage] pageSize：每页显示数量, currentPage：当前页数
    * @return: com.hanson.dto.PageBean<com.hanson.pojo.Goods> 商品对象封装在list集合里并用PageBean包装
    * @Date: 2020/4/12
    */
    public PageBean<GoodsTable> getGoodsSplit(Integer pageSize, Integer currentPage);

    /*
    * @description: 根据id批量删除商品
    * @params: [ids] 要删除的id
    * @return: com.hanson.dto.Message 删除成功返回处理成功信息，否则返回异常信息
    * @Date: 2020/4/13
    */
    public CommonResult<Integer> batchRemoveGoods(List<String> ids);




    /*
    * @description: 根据商品id修改商品信息
    * @params: [editTable] 要修改的商品（前端递过来的未经处理）
    * @return: com.hanson.common.api.CommonResult<java.lang.Object> 修改成功返回处理成功信息，否则返回异常信息
    * @throws: Exception
    * @Date: 2020/9/11
    */
    public CommonResult<Integer> updateGoods(GoodsEditTable editTable);


    /*
    * @description: 根据商品id修改商品状态，负责发布、下架 和重新审核商品操作
    * @params: [id, state] id：需要修改的商品，state：需要修改的商品状态代码，0：审核，1发布，2下架
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/11
    */
    public CommonResult<Integer> updateGoodsState(String id, Integer state);



    /*
    * @description: 分页模糊查询商品
    * @params: [pageSize, currentPage, key] pageSize：页面大小, currentPage：当前页, key：搜索关键字
    * @return: com.hanson.dto.PageBean<com.hanson.dto.GoodsTable> 返回封装查询到的商品对象
    * @Date: 2020/4/30
    */
    public PageBean<GoodsTable> getGoodsSplitWithSearch(Integer pageSize, Integer currentPage, String key);

    /*
    * @description: 计算商品总量
    * @params: []
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/8
    */
    public Integer countGoods();

    /*
    * @description: 批量修改商品信息
    * @params: [ids, state] ids：需要修改的商品id集合，state：需要更新的状态
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/11
    */
    public CommonResult<Integer> batchUpdateGoods(List<String> ids, Integer state);


    /*
    * @description: 根据商品id删除商品信息
    * @params: [id] 商品id
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/11
    */
    public CommonResult<Integer> deleteGoods(String id);
}
