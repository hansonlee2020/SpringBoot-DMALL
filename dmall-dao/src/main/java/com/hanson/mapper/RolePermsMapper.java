package com.hanson.mapper;

import com.hanson.pojo.vo.RolePerms;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @program: manager
 * @description: 角色-权限接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-15 18:04
 **/
@Mapper
@Repository
public interface RolePermsMapper {


    /*
    * @description: 查询全部角色-权限信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.vo.RolePerms>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<RolePerms> doGetPerms();

    /*
    * @description: 根据角色ID查询角色-权限
    * @params: [rid]
    * @return: java.util.List<com.hanson.pojo.vo.RolePerms>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<RolePerms> doGetPermsByRid(@Param("rid") Integer rid);

    /*
    * @description: 根据权限ID查询角色-权限
    * @params: [aid]
    * @return: java.util.List<com.hanson.pojo.vo.RolePerms>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<RolePerms> doGetPermsByAuthId(@Param("aid") Integer aid);

    /*
    * @description: 创建角色-权限
    * @params: [rid, aids]
    * rid：角色ID
    * aids：权限ID集合
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doCreateRolePerms(@Param("rid")Integer rid, @Param("aids")Set<Integer> aids);

    /*
    * @description: 更新角色权限
    * @params: [rp] 角色-权限对象
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doUpdateRolePerms(@Param("rp") RolePerms rp);

    /*
    * @description: 根据角色ID删除角色-权限
    * @params: [rid] 角色ID
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doDeleteRolePerms(@Param("rid") Integer rid);


    /*
    * @description: 精准删除角色-权限
    * @params: [rid, aid]
    * rid：角色ID
    * aid：权限ID
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doDeleteRolePermsAccurate(@Param("rid") Integer rid,@Param("aid")Integer aid);
}
