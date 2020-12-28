package com.hanson.service;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.pojo.dto.AdminEditTable;
import com.hanson.pojo.dto.SystemUser;
import com.hanson.pojo.vo.Authority;
import com.hanson.pojo.vo.User;
import com.hanson.pojo.vo.UserRole;

import java.util.List;


/**
 * @program: manager
 * @description: 用户service接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-04 19:03
 **/
public interface UserService {

    /*q
    --------------------------------------后台管理员登陆----------------------------------
     */

    /*
     * @description: 根据需要登陆的用户对象的用户名查找，验证该用户的合法性 ，并获取该用户的信息
     * @params: [login] 需要登陆的用户对象
     * @return: com.hanson.pojo.User 如果用户存在，以对象方式返回这个用户的信息，否则返回null
     * @Date: 2020/3/20
     */
    public User getLoginUser(User login);
    /*
     * @description: 根据用户名查找用户信息
     * @params: [userName] 用户名
     * @return: com.hanson.pojo.User 返回的用户信息
     * @Date: 2020/3/30
     */
    public User getLoginUserByName(String userName);

    /*q
    --------------------------------------后台管理员管理----------------------------------
     */

    /*
     * @description: 根据管理员用户id查询用户
     * @params: [id] 管理员用户id
     * @return: com.hanson.common.api.CommonResult<java.lang.Object>
     * @Date: 2020/4/23
     */
    public CommonResult<User> getUser(String id);


    /*
     * @description:  分页获取全部管理员信息
     * @params: [pageSize,currentPage] pageSize：分页大小,currentPage：当前页,key：搜索关键字
     * @return: com.hanson.common.api.PageResult<java.lang.Object>
     * @Date: 2020/4/23
     */
    public PageResult<List<SystemUser>> getUserSplit(Integer pageSize, Integer currentPage, String key);

    /*
     * @description: 新建管理员信息
     * @params: [aet] 管理员信息对象
     * @return: com.hanson.common.api.CommonResult<java.lang.Object>
     * @throws: Exception SQL执行异常
     * @Date: 2020/4/23
     */
    public CommonResult<Integer> createUser(AdminEditTable aet);



    /*
     * @description: 根据管理员用户id修改管理员信息
     * @params: [aet] 管理员信息对象
     * @return: com.hanson.common.api.CommonResult<java.lang.Object>
     * @throws: Exception SQL执行异常
     * @Date: 2020/4/23
     */
    public CommonResult<Integer> updateUser(AdminEditTable aet);


    /*
    * @description: 根据管理员id更新账户的状态信息
    * @params: [uid, state] uid：管理员id，state：账户的状态
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/18
    */
    public CommonResult<Integer> updateUserState(String uid, Integer state);


    /*
     * @description: 根据管理员用户id删除管理员
     * @params: [id] 管理员用户id
     * @return: com.hanson.common.api.CommonResult<java.lang.Object>
     * @throws: Exception SQL执行异常
     * @Date: 2020/4/23
     */
    public CommonResult<Integer> deleteUser(String id);


    /*
     * @description: 获取全部权限信息
     * @params: []
     * @return:
     * @throws: Exception
     * @Date: 2020/4/24
     */
    public CommonResult<List<Authority>> getAuthority();


    /*
     * @description: 批量授权
     * @params: [ids,userId] ids：权限id集合,userId：授权的用户id
     * @return: com.hanson.common.api.CommonResult<java.lang.Object>
     * @Date: 2020/4/24
     */
    public CommonResult<Integer> batchAuthorizePermissions(String ids, String userId);


    /*
     * @description:  批量撤销用户授权
     * @params: [ids, userId] ids：权限id集合,userId：撤销授权的用户id
     * @return: com.hanson.common.api.CommonResult<java.lang.Object>
     * @throws: Exception
     * @Date: 2020/4/24
     */
    public CommonResult<Integer> batchCancelPermission(String ids, String userId);


    /*
    * @description: 根据管理员id获取管理员拥有的角色
    * @params: [id] 管理员的id
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/17
    */
    public CommonResult<List<UserRole>> getUserRoles(String id);


    /*
    * @description: 根据管理员名获取管理员信息
    * @params: [name] 管理员名
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/17
    */
    public CommonResult<User> getUserByName(String name);
}
