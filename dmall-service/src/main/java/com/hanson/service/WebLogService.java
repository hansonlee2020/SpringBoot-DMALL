package com.hanson.service;


import com.hanson.common.api.PageResult;
import com.hanson.pojo.vo.WebLog;
import com.hanson.utils.PageUtil;

import java.util.Date;

/**
 * @Description 接口操作日志业务层接口
 * @Author Hanson
 * @Date 2020/11/30 17:33
 **/
public interface WebLogService {

    /*
    * 新建请求日志
    *
    * @params: [webLog]
    * @return: void
    * @Date: 2020/12/9
    */
    void createWebLog(WebLog webLog);

    /**
    *
    * 分页模糊查询接口操作日志
    *
    * @params: [pageUtil, search, start, end, pageResult]
    * @param: pageUtil  分页对象
    * @param: search    模糊查询关键字
    * @param: start     模糊查询开始日期
    * @param: end       模糊查询结束日期
    * @param: pageResult 模糊查询结果
    * @since: 2020/12/14
    **/
    void webLogSplit(PageUtil pageUtil, String search, Date start, Date end, PageResult pageResult);

}
