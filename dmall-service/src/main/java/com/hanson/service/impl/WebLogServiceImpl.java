package com.hanson.service.impl;

import com.hanson.common.api.PageResult;
import com.hanson.common.constant.Constant;
import com.hanson.mapper.WebLogMapper;
import com.hanson.pojo.vo.WebLog;
import com.hanson.service.WebLogService;
import com.hanson.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @CLassName WebLogServiceImpl
 * @Description 接口操作日志业务接口实现类
 * @Author Hanson
 * @Date 2020/11/30 17:34
 **/
@Service
public class WebLogServiceImpl implements WebLogService {

    private final static Logger logger = LoggerFactory.getLogger(WebLogServiceImpl.class);

    @Autowired
    private WebLogMapper webLogMapper;

    /**
    *
    * 创建日志
    *
    * @params: [webLog]
    * @param: webLog
    * @since: 2020/12/9
    **/
    @Override
    public void createWebLog(WebLog webLog) {
        webLogMapper.doCreateWebLog(webLog);
    }

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
    @Override
    public void webLogSplit(PageUtil pageUtil, String search, Date start, Date end, PageResult pageResult) {
        List<WebLog> webLogs = webLogMapper.doGetListWebLogSplit(pageUtil.getStart(), pageUtil.getSize(), search, start, end, Constant.DELETE_FLAG_CLOSE);
        Integer count;
        if (StringUtils.isNotBlank(search)){
            count = webLogMapper.doCountSearch(search, start, end);
        }else {
            count = webLogMapper.doCount();
        }
        pageResult.setCount(count);
        pageResult.setData(webLogs);
    }
}
