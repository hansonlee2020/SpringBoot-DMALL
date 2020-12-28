package com.hanson.mapper;

import com.hanson.pojo.vo.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 区表接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 21:17
 **/
@Mapper
@Repository
public interface AreaMapper {


    /*
    * @description: 查询全部区信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Area>
    * @Date: 2020/4/25
    */
    public List<Area> doGetAllAreas();



    /*
    * @description: 根据区id查询区信息
    * @params: [aid] 区id
    * @return: com.hanson.pojo.Area
    * @Date: 2020/4/25
    */
    public Area doGetAreaById(@Param("areaId") String aid);



    /*
    * @description: 根据市id查询区信息
    * @params: [cid] 市id
    * @return: java.util.List<com.hanson.pojo.Area>
    * @Date: 2020/4/25
    */
    public List<Area> doGetAreaByCityId(@Param("cityId") String cid);



    /*
    * @description: 根据区名查询区信息
    * @params: [name] 区名
    * @return: com.hanson.pojo.Area
    * @Date: 2020/4/25
    */
    public Area doGetAreaByName(@Param("areaName") String name);




    /*
    * @description: 分页查询区信息
    * @params: [startIndex, pageSize] startIndex：开始索引, pageSize：页大小
    * @return: java.util.List<com.hanson.pojo.Area>
    * @Date: 2020/4/25
    */
    public List<Area> doGetAreaSplit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
}
