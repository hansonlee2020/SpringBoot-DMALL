package com.hanson.mapper;

import com.hanson.pojo.vo.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 市表接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 21:06
 **/
@Mapper
@Repository
public interface CityMapper {


    /*
    * @description: 查询全部市信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.City>
    * @Date: 2020/4/25
    */
    public List<City> doGetAllCities();



    /*
    * @description: 根据市id查询市信息
    * @params: [cid] 市id
    * @return: com.hanson.pojo.City
    * @Date: 2020/4/25
    */
    public City doGetCityById(@Param("cityId") String cid);


    /*
    * @description: 根据省id查询市信息
    * @params: [pid] 省id
    * @return: java.util.List<com.hanson.pojo.City>
    * @Date: 2020/4/25
    */
    public List<City> doGetCityByProvinceId(@Param("provinceId") String pid);


    /*
    * @description: 根据市名查询市信息
    * @params: [name] 市明
    * @return: com.hanson.pojo.City
    * @Date: 2020/4/25
    */
    public City doGetCityByName(@Param("cityName") String name);



    /*
    * @description: 分页查询市信息
    * @params: [startIndex, pageSize] startIndex：开始索引, pageSize：页大小
    * @return: java.util.List<com.hanson.pojo.City>
    * @Date: 2020/4/25
    */
    public List<City> doGetCitiesSplit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
}
