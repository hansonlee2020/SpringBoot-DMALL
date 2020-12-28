package com.hanson.mapper;

import com.hanson.pojo.vo.LogisticsRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 订单物流信息接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 13:25
 **/
@Mapper
@Repository
public interface LogisticsRecordMapper {
    /*
    * @description: 获取全部订单物流记录信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.LogisticsRecord>
    * @Date: 2020/4/25
    */
    public List<LogisticsRecord> doGetALlLogisticsRecords();


    /*
    * @description: 根据物流号查询物流记录信息
    * @params: [logisticsId] 物流号
    * @return: com.hanson.pojo.LogisticsRecord
    * @Date: 2020/4/25
    */
    public LogisticsRecord doGetLogisticsRecordByRecordId(@Param("rid") String recordId);


    /*
    * @description: 根据物流公司id查询物流记录信息
    * @params: [logisticsId]
    * @return: java.util.List<com.hanson.pojo.vo.LogisticsRecord>
    * @throws: Exception
    * @Date: 2020/9/25
    */
    public List<LogisticsRecord> doGetLogisticsRecordByLogisticId(@Param("lid") Integer logisticsId);

    /*
    * @description: 新建物流记录信息
    * @params: [logisticsRecord] 物流信息对象
    * @return: java.lang.Integer 创建成功返回1，否则抛出异常信息
    * @throws: Exception SQL执行异常，违反唯一约束
    * @Date: 2020/4/25
    */
    public Integer doCreateLogisticsRecord(LogisticsRecord record);


    /*
    * @description: 更新物流记录信息
    * @params: [record] 物流记录对象
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doUpdateLogisticsRecord(@Param("record") LogisticsRecord record);

    /*
    * @description: 删除物流记录
    * @params: [record] 物理记录对象
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doDeleteLogisticsRecord(@Param("record")LogisticsRecord record);
}
