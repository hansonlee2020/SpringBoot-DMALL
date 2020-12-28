package com.hanson.mapper;

import com.hanson.pojo.vo.WebLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Description 系统接口操作日志记录接口
 * @Author Hanson
 * @Date 2020/11/30 16:08
 **/
@Mapper
@Repository
public interface WebLogMapper {

    /**
     * 根据id获取一条日志记录信息
     * @param id    主键id
     * @return
     */
    public WebLog doGetOneWebLogById(@Param("id") Long id);

    /**
     * 获取所有日志记录
     * @return
     */
    public List<WebLog> doGetListWebLog();

    /**
     * 分页模糊查询日志记录
     * @param startIndex    开始位置
     * @param pageSize      分页大小
     * @param key           模糊查询关键字，模糊查询列为操作日志名称
     * @param startTime     模糊查询日志创建时间的起始时间
     * @param endTime       模糊查询日志创建时间的结束时间
     * @param state         日志状态，默认为0-正常
     * @return
     */
    public List<WebLog> doGetListWebLogSplit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize, @Param("key") String key, @Param("start") Date startTime,@Param("end") Date endTime,@Param("state") Integer state);

    /**
     * 创建一条日志记录
     * @param webLog    日志对象
     * @return
     */
    public Integer doCreateWebLog(@Param("log") WebLog webLog);

    /**
     * 批量清理日志记录
     * @param fromId    清理比id大的日志记录
     * @param limit     清理日志数量，默认为1
     * @return
     */
    public Integer doBatchDeleteWebLog(@Param("from") Long fromId, @Param("limit") Integer limit);

    public Integer doCount();

    public Integer doCountSearch(@Param("search") String search,@Param("start") Date start,@Param("end") Date end);
}
