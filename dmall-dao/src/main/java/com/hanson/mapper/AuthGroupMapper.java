package com.hanson.mapper;

import com.hanson.pojo.vo.AuthGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: manager
 * @description: 权限组接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-17 19:11
 **/
@Mapper
@Repository
public interface AuthGroupMapper {

    /*
    * @description: 获取全部权限组信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.vo.AuthGroup>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<AuthGroup> doGetGroups();

    /*
    * @description: 根据权限组ID获取权限组信息
    * @params: [gid] 权限组ID
    * @return: com.hanson.pojo.vo.AuthGroup
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public AuthGroup doGetGroupById(@Param("gid") Integer gid);

    /*
    * @description: 根据权限组名称获取权限组信息
    * @params: [groupName] 权限组名称
    * @return: com.hanson.pojo.vo.AuthGroup
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public AuthGroup doGetGroupByName(@Param("gname") String groupName);

    /*
    * @description: 统计权限组数据
    * @params: []
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doCounts();

    /*
    * @description: 分页模糊查询权限组信息
    * @params: [start, size, key]
    * start：开始索引
    * size：分页大小
    * key：搜索关键字
    * @return: java.util.List<com.hanson.pojo.vo.AuthGroup>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<AuthGroup> doGroupSplit(@Param("start")Integer start,@Param("size") Integer size,@Param("key") String key);

    /*
    * @description: 模糊查询权限组信息
    * @params: [key] 查询关键字
    * @return: java.util.List<com.hanson.pojo.vo.AuthGroup>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<AuthGroup> doSearchGroup(@Param("key") String key);

    /*
    * @description: 创建权限组
    * @params: [groupName] 权限组名称
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doCreateAuthGroup(@Param("gname") String groupName);

    /*
    * @description: 更新权限组
    * @params: [groupId, groupName]
    * groupID：权限组ID
    * groupName：权限组名称
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doUpdateAuthGroup(@Param("gid") Integer groupId,@Param("gname") String groupName);

    /*
    * @description: 根据ID删除权限组
    * @params: [groupId] 权限ID
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doDeleteAuthGroup(@Param("gid") Integer groupId);

    /*
    * @description: 根据权限组名称删除权限组
    * @params: [groupName] 权限组名称
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doDeleteAuthGroupByName(@Param("gname") String groupName);
}
