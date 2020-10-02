package com.hanson.mapper;

import com.hanson.pojo.vo.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 省表接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 20:54
 **/
@Mapper
@Repository
public interface ProvinceMapper {


    /*
    * @description: 获取所有省份信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Province>
    * @Date: 2020/4/25
    */
    public List<Province> doGetAllProvinces();



    /*
    * @description: 根据省份id查询省份信息
    * @params: [pid]  省份id
    * @return: com.hanson.pojo.Province
    * @Date: 2020/4/25
    */
    public Province doGetProvinceById(@Param("provinceId") String pid);



    /*
    * @description: 根据省份名查询省份信息
    * @params: [name]  省份名
    * @return: com.hanson.pojo.Province
    * @Date: 2020/4/25
    */
    public Province doGetProvinceByName(@Param("provinceName") String name);



    /*
    * @description: 分页显示省份信息
    * @params: [startIndex, pageSize] startIndex：开始索引, pageSize：页大小
    * @return: java.util.List<com.hanson.pojo.Province>
    * @Date: 2020/4/25
    */
    public List<Province> doGetProvinceSplit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
}
