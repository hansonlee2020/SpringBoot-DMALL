package com.hanson.service.impl;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.exception.DmallException;
import com.hanson.mapper.LogisticsMapper;
import com.hanson.mapper.LogisticsRecordMapper;
import com.hanson.mapper.OrderMapper;
import com.hanson.pojo.vo.Logistics;
import com.hanson.pojo.vo.LogisticsRecord;
import com.hanson.pojo.vo.Order;
import com.hanson.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: manager
 * @description:
 * @param:
 * @author: Hanson
 * @create: 2020-09-25 08:37
 **/
@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private LogisticsMapper logisticsMapper;
    @Autowired
    private LogisticsRecordMapper recordMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageResult<List<Logistics>> getLogisticsSplit(Integer page, Integer limit, String search) {
        String key;
        int total;
        if (search == null || "".equals(search.replaceAll(" ",""))){
            key = null;
            total = logisticsMapper.doCounts(null);
        }else {
            key = search.replaceAll(" ","");
            total = logisticsMapper.doSearchLogistics(key).size();
        }
        if (total <= 0) throw new NullPointerException("暂无匹配的数据");
        int start = (page - 1) * limit;
        List<Logistics> logistics = logisticsMapper.doSplitLogistics(start, limit, key);
        return new PageResult<>(ResultCode.SUCCESS,total,logistics);
    }

    @Override
    public CommonResult<Logistics> getLogisticsByName(String name) {
        Logistics logistics = logisticsMapper.doGetLogisticsByName(name);
        return new CommonResult<>(ResultCode.SUCCESS,logistics);
    }

    @Override
    public CommonResult<Logistics> getLogisticsById(Integer logisticsId) {
        Logistics logistics = logisticsMapper.doGetLogisticsById(logisticsId);
        return new CommonResult<>(ResultCode.SUCCESS,logistics);
    }

    @Override
    public CommonResult<Integer> createLogistics(Logistics logistics) {
        Integer cnum = logisticsMapper.doCreateLogistics(logistics);
        if (cnum <= 0) return new CommonResult<>(ResultCode.FAILED,cnum);
        return new CommonResult<>(ResultCode.SUCCESS,cnum);
    }

    @Override
    public CommonResult<Integer> updateLogistics(Logistics logistics) {
        if (logistics == null || "".equals(logistics.getLogisticsName().replaceAll(" ",""))) throw new NullPointerException("物流名称为空");
        Integer unum = logisticsMapper.doUpdateLogistics(logistics);
        if (unum <= 0) return new CommonResult<>(ResultCode.FAILED,unum);
        return new CommonResult<>(ResultCode.SUCCESS,unum);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> deleteLogistics(Logistics logistics, boolean isChecked) {
        if (logistics == null) throw new NullPointerException("物流为空");
        //检查物流是否正在使用
        List<LogisticsRecord> records = recordMapper.doGetLogisticsRecordByLogisticId(logistics.getId());
        if (!isChecked && records != null && records.size() > 0){
            //正在使用物流，询问是否继续删除物流
            String msg = "统计发现：有 ";

            msg += records.size() + " 条物流记录信息正在使用该物流，继续删除将清理使用了该物流的记录信息，是否继续删除？";
            CommonResult<Integer> temp = new CommonResult<>(ResultCode.FAILED, records.size(),msg);
            temp.setType("sure");
            return temp;
        }
        int count = 0;
        if (records != null){
            //删除物流记录信息
            for (LogisticsRecord record : records) {
                //删除订单的物流单号并回收订单
                Order order = orderMapper.doGetOrderByRecordId(record.getRecordId());
                if (order != null){
                    orderMapper.doUpdateOrderLogistics(order.getOrderId(),null);
                    orderMapper.doUpdateOrder(order.getOrderId(), Constant.RUBBISH_STATE);
                }
                Integer dnum = recordMapper.doDeleteLogisticsRecord(record);
                if (dnum > 0) count ++;
            }
            if (count < records.size()) throw new DmallException("清理物流记录出错，共" + records.size() + "条，可清理"+ count + "条。");
        }
        //删除物流公司
        Integer dnum = logisticsMapper.doDeleteLogistics(logistics);
        if (dnum <= 0) return new CommonResult<>(ResultCode.FAILED,dnum);
        return new CommonResult<>(ResultCode.SUCCESS,null,"物流已删除，共清理了" + count + "条物流记录。");
    }
}
