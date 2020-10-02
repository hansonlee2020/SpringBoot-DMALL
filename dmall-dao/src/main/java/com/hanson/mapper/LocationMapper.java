package com.hanson.mapper;

import com.hanson.pojo.vo.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 位置信息接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 19:53
 **/
@Mapper
@Repository
public interface LocationMapper {


    /*
    * @description: 获取全部位置信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Location>
    * @Date: 2020/4/25
    */
    public List<Location> doGetAllLocations();


    /*
    * @description: 根据位置id查询位置信息
    * @params: [lid]  位置id
    * @return: com.hanson.pojo.Location 位置信息
    * @Date: 2020/4/25
    */
    public Location doGetLocationById(@Param("locationId") Integer lid);


    /*
    * @description: 创建位置信息
    * @params: [location]  位置对象
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/4/25
    */
    public Integer doCreateLocation(Location location);


    /*
    * @description: 清空位置表
    * @params: []
    * @return: java.lang.Integer
    * @Date: 2020/5/13
    */
    public Integer doDeleteLocation();
}
