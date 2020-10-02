package com.hanson.common.constant;

/**
 * @program: manager
 * @description: 常量
 * @param:
 * @author: Hanson
 * @create: 2020-09-09 16:23
 **/
public interface Constant {
    String ERROR="系统处理异常，请联系系统管理员！";

    /*q
    ----------------商品状态----------------
     */
    String AUDITING = "审核中";//状态为0
    String RELEASED = "已发布";//状态为1
    String OFF_SHELF = "已下架";//状态为2
    String DEFAULT= "无效状态";//状态为null
    Integer AUDITING_STATE = 0;//状态为0
    Integer RELEASED_STATE = 1;//状态为1
    Integer OFF_SHELF_STATE = 2;//状态为2
    Integer DEFAULT_STATE = null;//状态为null

    /*q
    -----------------分类信息默认----------------
     */
    String CATEGORY= "默认分类";

    /*q
    -----------------商品操作信息-----------------
     */
    String ADD_GOODS_SUCCESS="添加商品成功";
    String ADD_GOODS_FAILED="添加商品失败";
    String UPDATE_GOODS_SUCCESS="修改商品信息成功";
    String UPDATE_GOODS_FAILED="修改商品信息失败";
    String DELETE_GOODS_SUCCESS="删除商品成功";
    String DELETE_GOODS_FAILED="删除商品失败";
    String QUERY_GOODS_SUCCESS="查询商品信息成功";
    String QUERY_GOODS_FAILED="查询商品信息失败";
    String SPLIT_GOODS_SUCCESS="商品信息分页成功";
    String SPLIT_GOODS_FAILED="商品信息分页失败";
    String RELEASES_GOODS_SUCCESS="批量发布商品信息成功";
    String RELEASES_GOODS_FAILED="批量发布商品信息失败";
    String RELEASE_GOODS_SUCCESS="发布商品信息成功";
    String RELEASE_GOODS_FAILED="发布商品信息失败";
    String OFF_SHELFS_GOODS_SUCCESS="批量下架商品信息成功";
    String OFF_SHELFS_GOODS_FAILED="批量下架商品信息失败";
    String OFF_SHELF_GOODS_SUCCESS="下架商品信息成功";
    String OFF_SHELF_GOODS_FAILED="下架商品信息失败";
    String AUDITINGS_GOODS_SUCCESS="批量审核商品信息成功";
    String AUDITINGS_GOODS_FAILED="批量审核商品信息失败";
    String AUDITING_GOODS_SUCCESS="审核商品信息成功";
    String AUDITING_GOODS_FAILED="审核商品信息失败";
    String DELETES_GOODS_SUCCESS="批量删除商品信息成功";
    String DELETES_GOODS_FAILED="批量删除商品信息失败";
    /*q
    ----------------用户状态----------------------
     */
    String ENABLE = "已启用";
    String DISABLE = "已停用";
    String RECYCLE = "回收";
    String NON = "无效";
    int ENABLE_STATE = 1;
    int DISABLE_STATE = 0;
    int RECYCLE_STATE = 2;
    int NON_STATE = -1;//无效的状态，代表防止整表更新的状态

    /*q
    -----------------------用户性别-------------
     */
    String FEMALE = "女";
    String MALE = "男";
    String SECRECY = "保密";
    int FEMALE_STATE = 0;
    int MALE_STATE = 1;
    int SECRECY_STATE = 2;


    /*q
    ---------------------订单状态--------------------
     */
    String SUCCESS="交易完成";      //订单状态为1
    String RECEIVED="已签收";      //订单状态为2
    String TO_BE_RECEIVED="待签收";//订单状态为3
    String DELIVERED="已发货";     //订单状态为4
    String PAID="已支付";          //订单状态为5
    String TO_BE_PAID="待支付";    //订单状态为6
    String CLOSE="交易取消";        //订单状态为7
    String RETURN="发起退单";       //退单，订单状态为8
    String RUBBISH="已回收";    //订单状态为1000
    int SUCCESS_STATE=1;            //订单状态为1
    int RECEIVED_STATE=2;           //订单状态为2
    int TO_BE_RECEIVED_STATE=3;     //订单状态为3
    int DELIVERED_STATE=4;          //订单状态为4
    int PAID_STATE=5;               //订单状态为5
    int TO_BE_PAID_STATE=6;         //订单状态为6
    int CLOSE_STATE=7;              //订单状态为7
    int RETURN_STATE=8;             //订单状态为8
    int RUBBISH_STATE=1000;    //订单状态为1000

    /*q
    ------------------------订单操作-----------------
     */
    String RECYCLE_ORDER_SUCCESS = "回收订单成功";
    String RECYCLE_ORDER_FAILED = "回收订单失败";
    String DELETE_ORDER_SUCCESS = "垃圾订单已清理";
    String DELETE_ORDER_FAILED = "垃圾订单清理失败";
    String RECOVERY_ORDER_SUCCESS = "订单已恢复";
    String RECOVERY_ORDER_FAILED = "订单恢复失败";
    String RECOVERIES_ORDER_SUCCESS = "订单批量恢复成功";
    String RECOVERIES_ORDER_FAILED = "订单批量恢复失败";
    String RECYCLES_ORDER_SUCCESS = "批量回收订单成功";
    String RECYCLES_ORDER_FAILED = "批量回收订单失败";
    String NOTES_ORDER_SUCCESS = "修改订单备注信息成功";
    String NOTES_ORDER_FAILED = "修改订单备注信息失败";
    String DELIVER_ORDER_SUCCESS = "已发货";
    String DELIVER_ORDER_FAILED = "发货失败";


    /*q
    --------------------------物流操作-------------------
     */
    String UPDATE_LOGISTICSRECORD_SUCCESS = "更新物流信息成功";
    String UPDATE_LOGISTICSRECORD_FAILED = "更新物流信息失败";



    /*q
    ------------------------日期格式-----------------
     */
    String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

    /*q
    ---------------------------角色----------------
     */
    int SYS = 1;  //超级管理员
    int ADMIN = 2;  //普通管理员
    int TEST = 3;   //测试员
    int GUEST = 4;  //游客


    /*q
    ---------------------------管理员状态----------------
     */
    //启用停用复用上面的用户状态即可
    String FREEZE = "冻结";//用户状态为3
    int FREEZE_STATE = 3;


    /*q
    ---------------------------管理员管理操作
     */
    String UPDATE_STATE_SUCCESS = "更新管理员状态成功";
    String UPDATE_STATE_FAILED = "更新管理员状态失败";
    String DELETE_ADMIN_SUCCESS = "管理员已删除";
    String DELETE_ADMIN_FAILED = "删除管理员失败";

    String MD5 = "MD5";//加密方式
    String FACTOR = "dmallSaltValueFactor";//原始盐值因子
    String MAP_PASS = "password";//加密返回的map保存的密码的key：password
    String MAP_SALT = "salt";//加密返回的map保存的盐值的key：salt
    int ITERATIONS_PASS = 2;//密码加密迭代次数
    int ITERATIONS_SALT = 3;//盐值加密迭代次数


    /*q
    ---------------------------角色操作------------------
     */
    String CREATE_ROLE_SUCCESS = "角色已创建";
    String CREATE_ROLE_FAILED = "创建角色失败";
    String DELETE_ROLE_SUCCESS = "角色已删除";
    String DELETE_ROLE_FAILED = "删除角色失败";


    /*q
    --------------------------权限-----------------------
     */
    String AUTH_DEFAULT = "默认";


    /*q
    -----------------------权限组----------------------
     */
    String DEFAULT_AUTH_GROUP = "默认";//默认权限组



    /*q
    --------------------排序默认值--------------------
     */
    int SORT_DEFAULT = 5;
}
