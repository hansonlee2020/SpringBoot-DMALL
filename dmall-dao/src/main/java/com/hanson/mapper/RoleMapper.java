package com.hanson.mapper;

import com.hanson.pojo.vo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: manager
 * @description: 角色dao接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-15 17:04
 **/
@Mapper
@Repository
public interface RoleMapper {


    /*
    * @description: 根据角色ID获取角色
    * @params: [id] 角色ID
    * @return: com.hanson.pojo.vo.Role
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Role doGetRoleById(@Param("rid") Integer id);


    /*
    * @description: 根据角色名称获取角色
    * @params: [name] 角色名称
    * @return: com.hanson.pojo.vo.Role
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Role doGetRoleByName(@Param("rname") String name);

    /*
    * @description: 查询全部角色
    * @params: []
    * @return: java.util.List<com.hanson.pojo.vo.Role>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<Role> doGetRoles();


    /*
    * @description: 查询启用中的角色
    * @params: [ActiveState] 启用状态
    * @return: java.util.List<com.hanson.pojo.vo.Role>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<Role> doGetActiveRoles(@Param("state") Integer ActiveState);


    /*
    * @description: 创建角色
    * @params: [role] 角色对象
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doCreateRole(@Param("role") Role role);


    /*
    * @description:  更新角色
    * @params: [role] 角色对象
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doUpdateRole(@Param("role") Role role);

    /*
    * @description: 删除角色
    * @params: [rid] 角色ID
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doDeleteRole(@Param("rid") Integer rid);

    /*
    * @description: 统计角色数据
    * @params: []
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doCounts();


    /*
    * @description: 统计启用中的角色数据
    * @params: [ActiveState] 启用状态
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doCountsActive(@Param("state") Integer ActiveState);

    /*
    * @description: 分页模糊查询角色名称
    * @params: [start, size, key]
    * start：开始索引
    * size：分页大小
    * key：查询关键字
    * @return: java.util.List<com.hanson.pojo.vo.Role>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<Role> doRoleSplit(@Param("start")Integer start,@Param("size")Integer size,@Param("key")String key);


    /*
    * @description: 模糊查询角色名
    * @params: [key] 查询关键字
    * @return: java.util.List<com.hanson.pojo.vo.Role>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<Role> doRoleSearch(@Param("key")String key);
}
