package com.hanson.mapper;

import com.hanson.pojo.vo.Logistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.logging.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 物流公司表dao接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 12:34
 **/
@Mapper
@Repository
public interface LogisticsMapper {
    /*
    * @description: 获取全部物流公司信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Logistics>
    * @Date: 2020/4/25
    */
    public List<Logistics> doGetAllLogistics(@Param("state") Integer state);



    /*
    * @description: 根据物流公司名查询物流表
    * @params: [name] 物流公司名
    * @return: com.hanson.pojo.Logistics
    * @Date: 2020/4/25
    */
    public Logistics doGetLogisticsByName(@Param("name") String name);


    /*
    * @description:  根据物流id查询物流表
    * @params: [id] 物流id
    * @return: com.hanson.pojo.Logistics
    * @Date: 2020/4/25
    */
    public Logistics doGetLogisticsById(@Param("id") Integer id);



    /*
    * @description: 创建物流
    * @params: [logistics] 物流对象
    * @return: java.lang.Integer
    * @Date: 2020/4/25
    */
    public Integer doCreateLogistics(@Param("logistics") Logistics logistics);



    /*
    * @description: 更新物流
    * @params: [logistics] 物流对象
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/25
    */
    public Integer doUpdateLogistics(@Param("logistics")Logistics logistics);


    /*
    * @description: 删除物流
    * @params: [logistics]
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/25
    */
    public Integer doDeleteLogistics(@Param("logistics")Logistics logistics);

    /*
    * @description: 分页查询物流
    * @params: [start, size, search]
    * @return: java.util.List<com.hanson.pojo.vo.Logistics>
    * @throws: Exception
    * @Date: 2020/9/25
    */
    public List<Logistics> doSplitLogistics(@Param("start") Integer start,@Param("size")Integer size,@Param("key")String key);


    /*
    * @description: 模糊查询物流公司名称信息
    * @params: [key] 查询关键字
    * @return: java.util.List<com.hanson.pojo.vo.Logistics>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<Logistics> doSearchLogistics(@Param("key")String key);


    /*
    * @description: 根据状态统计数据量
    * @params: [state]
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doCounts(@Param("state") Integer state);
}
