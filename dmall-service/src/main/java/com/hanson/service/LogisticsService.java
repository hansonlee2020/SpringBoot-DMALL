package com.hanson.service;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.pojo.vo.Logistics;

import java.util.List;

/**
 * @program: manager
 * @description: 物流公司接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-25 08:18
 **/
public interface LogisticsService {

    public PageResult<List<Logistics>> getLogisticsSplit(Integer page, Integer limit, String search);
    public CommonResult<Logistics> getLogisticsByName(String name);
    public CommonResult<Logistics> getLogisticsById(Integer logisticsId);
    /*
     * @description: 新增物流公司信息
     * @params: [name] 物流名称
     * @return: com.hanson.common.api.CommonResult<java.lang.Object> 返回处理结果
     * @Date: 2020/4/25
     */
    public CommonResult<Integer> createLogistics(Logistics logistics);
    public CommonResult<Integer> updateLogistics(Logistics logistics);
    public CommonResult<Integer> deleteLogistics(Logistics logistics, boolean isChecked);
}
