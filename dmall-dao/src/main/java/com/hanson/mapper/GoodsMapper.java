package com.hanson.mapper;

import com.hanson.pojo.vo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 商品dao层接口
 * @param:
 * @author: Hanson
 * @create: 2020/9/8
 **/
@Mapper
@Repository
public interface GoodsMapper {
    /*
    * @description: 获取全部商品
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Goods> 返回List集合对象，对象类型为Goods
    * @Date: 2020/3/27
    */
    public List<Goods> doGetGoods();


    /*
    * @description: 添加新商品
    * @params: [goods] 需要添加的商品对象信息
    * @return: java.lang.Integer 添加成功返回1，否则返回0
    * @Date: 2020/4/7
    */
    public Integer doAddGoods(Goods goods);


    /*
    * @description: 根据商品名称查询商品，可以用于比对商品的相似度
    * @params: [name] 要查询的商品名称
    * @return: java.util.List<com.hanson.pojo.Goods> 返回商品对象，list集合
    * @Date: 2020/4/8
    */
    public List<Goods> doGetGoodsByName(@Param("productName") String name);


    /*
    * @description: 根据商品id查询商品
    * @params: [id] 要查询的商品id
    * @return: com.hanson.pojo.Goods 查询到的商品信息
    * @Date: 2020/4/13
    */
    public Goods doGetGoodsById(@Param("id") String id);



    /*
    * @description: 分页查询所有商品
    * @params: [limit, pageSize] index:开始索引，pageSize:页大小
    * @return: java.util.List<com.hanson.pojo.Goods> 查询到的商品对象集合list
    * @Date: 2020/4/11
    */
    public List<Goods> doGetGoodsSplit(@Param("startIndex") Integer index, @Param("pageSize") Integer pageSize);


    /*
    * @description: 统计商品总数据量
    * @params: []
    * @return: java.lang.Integer 商品总量
    * @Date: 2020/4/11
    */
    public Integer doGetCounts();




    /*
     * @description: 根据分类id查询商品信息，负责分类删除时检查分类作为外键被商品使用的情况
     * @params: [id] 分类id
     * @return: java.util.List<com.hanson.pojo.Goods> 使用分类id所在分类的所有商品
     * @Date: 2020/4/18
     */
    public List<Goods> doGetGoodsByCategoryId(@Param("categoryId") Integer id);



    /*
    * @description: 根据id批量删除商品
    * @params: [ids] 要删除的id，Set集合
    * @return: java.lang.Integer 成功删除的数据数量,删除失败返回0
    * @Date: 2020/4/13
    */
    public Integer doBatchDeleteGoods(@Param("ids") Set<String> ids);


    /*
    * @description: 根据商品id删除商品信息
    * @params: [id] 要删除的id
    * @return: java.lang.Integer 删除成功返回1，否则返回0
    * @Date: 2020/4/13
    */
    public Integer doDeleteGoods(@Param("id") String id);


    /*
    * @description: 根据商品id批量更新商品状态
    * @params: [ids, state] ids：需要更新的商品id集合，state：更新后的状态
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/11
    */
    public Integer doBatchUpdateGoods(@Param("ids") Set<String> ids, @Param("state") Integer state);


    /*
    * @description: 根据商品id修改商品信息
    * @params: [goods] 要修改的商品
    * @return: java.lang.Integer 删除成功返回1，否则返回0
    * @Date: 2020/4/15
    */
    public Integer doUpdateGoods(Goods goods);



    /*
    * @description: 根据商品id修改商品的状态
    * @params: [id , state] id:要修改的商品id,state：修改的状态代码
    * @return: java.lang.Integer 修改成功返回1，否则返回0
    * @throws: Exception
    * @Date: 2020/4/16
    */
    public Integer doUpdateGoodsState(@Param("productId") String id, @Param("state") Integer state);




    /*
    * @description: 分页模糊查询
    * @params: [index, pageSize, key] index：开始索引, pageSize：页面大小, key：搜索关键字
    * @return: java.util.List<com.hanson.pojo.Goods> 查询到的商品对象
    * @throws: Exception
    * @Date: 2020/4/30
    */
    public List<Goods> doGetGoodsSplitWithSearch(@Param("startIndex") Integer index, @Param("pageSize") Integer pageSize, @Param("searchKey") String key);




    /*
    * @description: 模糊查询
    * @params: [key] key：查询关键字
    * @return: java.util.List<com.hanson.pojo.Goods> 查询到的商品对象
    * @throws: Exception
    * @Date: 2020/4/30
    */
    public List<Goods> doGetGoodsWithSearch(@Param("searchKey") String key);


    /*
    * @description: 查询正在使用分类的商品信息
    * @params: [cateId]
    * @return: java.util.List<com.hanson.pojo.vo.Goods>
    * @throws: Exception
    * @Date: 2020/9/22
    */
    public List<Goods> doGetGoodsByCateId(@Param("cid")Integer cateId);
}
