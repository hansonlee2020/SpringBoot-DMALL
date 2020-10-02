package com.hanson;

import com.hanson.common.constant.Constant;
import com.hanson.mapper.*;
import com.hanson.pojo.vo.*;
import com.hanson.service.*;
import com.hanson.service.impl.SystemServiceImpl;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class DmallWebApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    SystemServiceImpl systemService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    ProvinceMapper provinceMapper;
    @Autowired
    CityMapper cityMapper;
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    LogisticsMapper logisticsMapper;
    @Autowired
    LogisticsRecordMapper recordMapper;
    @Autowired
    MemberOrderMapper memberOrderMapper;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderService orderService;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RolePermsMapper rolePermsMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    UserService userService;
    @Autowired
    AuthorityMapper authorityMapper;
    @Autowired
    AuthGroupMapper authGroupMapper;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;

    private static ThreadLocal<SimpleDateFormat> THREAD_LOCAL = ThreadLocal.withInitial(SimpleDateFormat::new);

    @Test
    void contextLoads() {
        /*SimpleHash md5 = new SimpleHash("MD5", "player");
        System.out.println(md5);*/
        /*System.out.println("counts=" + roleMapper.doCounts());
        System.out.println("activeCounts=" + roleMapper.doCountsActive(Constant.ENABLE_STATE));
        System.out.println("创建前先删除num= " + roleMapper.doDeleteRole(99));
        System.out.println("增加num = " + roleMapper.doCreateRole(new Role(99,Constant.ENABLE_STATE,"99")));
        System.out.println("查询by id= " + roleMapper.doGetRoleById(99));
        System.out.println("查询by name= " + roleMapper.doGetRoleByName("99"));
        System.out.println("更新= " + roleMapper.doUpdateRole(new Role(99, Constant.ENABLE_STATE, "88")));
        System.out.println("更新后查询by id= " + roleMapper.doGetRoleById(99));
        System.out.println("删除num= " + roleMapper.doDeleteRole(99));*/
        /*System.out.println("总授权=" + rolePermsMapper.doGetPerms());
        System.out.println("角色1总授权=" + rolePermsMapper.doGetPermsByRid(1));
        System.out.println("添加前删除=" + rolePermsMapper.doDeleteRolePerms(2));
        System.out.println("添加=" + rolePermsMapper.doCreateRolePerms(new RolePerms(2,1)));
        System.out.println("更新=" + rolePermsMapper.doUpdateRolePerms(new RolePerms(2,3)));
        System.out.println("更新后查询=" + rolePermsMapper.doGetPermsByRid(1));
        System.out.println("添加前删除=" + rolePermsMapper.doDeleteRolePerms(2));*/
        /*System.out.println("doGetLoginUserByName=" + userMapper.doGetLoginUserByName("system"));
        System.out.println("doGetLoginUser=" + userMapper.doGetLoginUser(new User("system","123456")));
        System.out.println("doGetUserByName=" + userMapper.doGetUserByName("system"));
        System.out.println("doGetUserById=" + userMapper.doGetUserById("1"));
        System.out.println("doCounts=" + userMapper.doCounts());
        System.out.println("doDeleteUser=" + userMapper.doDeleteUser("0"));
        System.out.println("doCreateUser=" + userMapper.doCreateUser(new User("99","99")));
        System.out.println("doUpdateUser=" + userMapper.doUpdateUser(new User("99","88")));
        System.out.println("更新后doGetUserByName=" + userMapper.doGetUserByName("99"));
        System.out.println("更新doUpdateUserState=" + userMapper.doUpdateUserState("0",1));
        System.out.println("更新状态后doGetUserByName=" + userMapper.doGetUserByName("99"));
        System.out.println("doGetUsers的size=" + userMapper.doGetUsers().size());
        System.out.println("doSearchUserByName的size=" + userMapper.doSearchUserByName("9").size());
        System.out.println("空搜索doUserSplit=" + userMapper.doUserSplit(0,2,null));
        System.out.println("关键字搜索doUserSplit=" + userMapper.doUserSplit(0,2,"9"));
        System.out.println("完成测试后删除doDeleteUser=" + userMapper.doDeleteUser("0"));*/
        /*System.out.println("doGetUserRoles=" + userRoleMapper.doGetUserRoles());
        System.out.println("doGetUserRolesByUid=" + userRoleMapper.doGetUserRolesByUid("1"));
        System.out.println("创建前删除doDeleteUserRole=" + userRoleMapper.doDeleteUserRole("9"));
        System.out.println("doCreateUserRole=" + userRoleMapper.doCreateUserRole(new UserRole("9",1)));
        System.out.println("更新前查询doGetUserRolesByUid=" + userRoleMapper.doGetUserRolesByUid("9"));
        System.out.println("doGetUserRoles=" + userRoleMapper.doUpdateUserRole(new UserRole("9",2)));
        System.out.println("更新后查询doGetUserRolesByUid=" + userRoleMapper.doGetUserRolesByUid("9"));
        System.out.println("doCounts=" + userRoleMapper.doCounts());
        System.out.println("更新后删除doDeleteUserRole=" + userRoleMapper.doDeleteUserRole("9"));*/
        /*PageResult<Object> userSplit = userService.getUserSplit(10, 1, null);
        Object data = userSplit.getData();
        if (data instanceof List<?>) {
            System.out.println(((List<?>) data).size());
            for (Object datum : (List<?>) data) {
                if (datum instanceof SystemUser){
                    System.out.println(datum.toString());
                }
            }
        }
        System.out.println();*/
        /*List<UserRole> roles = userRoleMapper.doGetUserRolesByUid("1");
        ArrayList<Integer> rid = new ArrayList<>();
        for (UserRole role : roles) {
            rid.add(role.getRoleId());
        }
        for (int i = 1; i <= 4; i++) {
            UserRole userRole = new UserRole("1",i);
            if (!rid.contains(i)) userRoleMapper.doCreateUserRole(userRole);
        }
        rid.clear();
        int count = 0;
        List<RolePerms> perms = rolePermsMapper.doGetPermsByRid(1);
        ArrayList<Integer> perm = new ArrayList<>();
        for (RolePerms rolePerms : perms) {
            perm.add(rolePerms.getAuthorityId());
        }
        for (int i = 1; i <= 62; i++) {
            RolePerms rolePerms = new RolePerms(1,i);
            if (!perm.contains(i)){
                Integer res = rolePermsMapper.doCreateRolePerms(rolePerms);
                if (res > 0) count++;
            }
        }
        perm.clear();
        System.out.println(count);*/
        /*List<UserRole> i1 = new ArrayList<>();
        UserRole u1 = new UserRole("1",1);
        UserRole u2 = new UserRole("1",2);
        UserRole u3 = new UserRole("1",3);
        i1.add(u1);
        i1.add(u2);
        i1.add(u3);
        List<UserRole> i2 = new ArrayList<>(i1);
        System.out.println("========修改userRole前的i1 List集合========");
        for (UserRole role : i1) {
            System.out.println(role);
        }
        System.out.println("========修改userRole前的i2 List集合========");
        for (UserRole role : i2) {
            System.out.println(role);
        }
        u1.setRoleId(5);
        u2.setRoleId(6);
        u3.setRoleId(7);
        System.out.println("========修改userRole后的i1 List集合========");
        for (UserRole role : i1) {
            System.out.println(role);
        }
        System.out.println("========修改userRole后的i2 List集合========");
        for (UserRole role : i2) {
            System.out.println(role);
        }*/
        /*HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        Integer res = userRoleMapper.doCreateUserRoles("8", set);
        System.out.println(res);
        System.out.println(userRoleMapper.doDeleteUserRoles("8", set));*/
        /*System.out.println("doGetAuthorities=" +authorityMapper.doGetAuthorities().size());
        System.out.println("前doDeleteAuthorityById="+authorityMapper.doDeleteAuthorityById(999));
        System.out.println("doCreateAuthority="+authorityMapper.doCreateAuthority(new Authority(999, "ddd", "ddd", "ddd")));
        System.out.println("前doGetAuthorityById="+authorityMapper.doGetAuthorityById(999));
        System.out.println("doGetAuthorityByName="+authorityMapper.doGetAuthorityByName("ddd"));
        System.out.println("doGetAuthorityByField="+authorityMapper.doGetAuthorityByField("ddd"));
        System.out.println("doUpdateAuthority="+authorityMapper.doUpdateAuthority(new Authority(999, "fff", "fff", "fff")));
        System.out.println("后doGetAuthorityById"+authorityMapper.doGetAuthorityById(999));
        System.out.println("doDeleteAuthorityById="+authorityMapper.doDeleteAuthorityById(999));*/
        /*System.out.println(authGroupMapper.doGetGroups().size());
        System.out.println(authGroupMapper.doGetGroupById(1));
        System.out.println(authGroupMapper.doCounts());*/
        /*CommonResult<Object> auths = userRoleService.getAuths("system");
        Object data = auths.getData();
        if (data instanceof List<?>) {
            System.out.println(((List<?>) data).size());
            for (Object datum : ((List<?>) data)) {
                System.out.println(datum);
            }
        }*/
        /*System.out.println("删除：" + authorityMapper.doDeleteAuthorityById(999));
        System.out.println("创建：" + authorityMapper.doCreateAuthority(new Authority(999, "aaa", "aaa", 1, "aaa")));
        System.out.println("ById：" +  authorityMapper.doGetAuthorityById(999));
        System.out.println("ByName：" +  authorityMapper.doGetAuthorityByName("aaa"));
        System.out.println("ByField：" +  authorityMapper.doGetAuthorityByField("aaa"));
        System.out.println("ByField：" +  authorityMapper.doUpdateAuthority(new Authority(999, "fff", "fff", 1, "fff")));
        System.out.println("后ById：" +  authorityMapper.doGetAuthorityById(999));
        System.out.println("删除：" + authorityMapper.doDeleteAuthorityById(999));
        System.out.println("size=" + authorityMapper.doGetAuthority().size());
        System.out.println("Search size=" + authorityMapper.doSearchAuthority("添加").size());
        for (Authority authority : authorityMapper.doSplitAuthority(0, 5, "添加")) {
            System.out.println(authority);
        }*/
        /*System.out.println(authGroupMapper.doDeleteAuthGroupByName("ddd"));
        System.out.println(authGroupMapper.doCreateAuthGroup("ddd"));
        AuthGroup authGroup = authGroupMapper.doGetGroupByName("ddd");
        System.out.println(authGroupMapper.doUpdateAuthGroup(authGroup.getGroupId(),"fff"));
        System.out.println(authGroupMapper.doGetGroupById(authGroup.getGroupId()));
        System.out.println(authGroupMapper.doDeleteAuthGroupByName("fff"));*/
        /*System.out.println(goodsCategoryMapper.doDeleteGoodsCategory(1));
        System.out.println(goodsCategoryMapper.doCreateGoodsCategory(new GoodsCategory(null, "家用电器", null)));
        System.out.println(goodsCategoryMapper.doGetGoodsCategoryByName("家用电器"));
        System.out.println(goodsCategoryMapper.doUpdateGoodsCategory(new GoodsCategory(1, "母婴", null)));
        System.out.println(goodsCategoryMapper.doGetGoodsCategoryByCategoryId(1));
        System.out.println(goodsCategoryMapper.doDeleteGoodsCategory(1));*/
        /*initCategory();*/
       /* System.out.println(orderService.getOrderDetailsForPrint("1600946499042"));
        System.out.println(initMemberOrder());
        System.out.println(initOrder());*/
        /*System.out.println(initAuthority());*/
        /*Logistics logistics = new Logistics(null,"测试物流",1,Constant.ENABLE_STATE);
        System.out.println("doDeleteLogistics=" + logisticsMapper.doDeleteLogistics(logistics));
        System.out.println("doCreateLogistics="+ logisticsMapper.doCreateLogistics(logistics));
        System.out.println("doGetLogisticsByName=" + logisticsMapper.doGetLogisticsByName("测试物流"));
        logistics.setLogisticsName("更新了物流");
        Logistics lg = logisticsMapper.doGetLogisticsByName("测试物流");
        logistics.setId(lg.getId());
        System.out.println("doUpdateLogistics="+ logisticsMapper.doUpdateLogistics(logistics));
        System.out.println("doGetLogisticsByName=" +logisticsMapper.doGetLogisticsByName("更新了物流"));
        System.out.println("===========================");
        System.out.println("doDeleteLogistics=" +logisticsMapper.doDeleteLogistics(logistics));
        System.out.println("doSplitLogistics.size=" +logisticsMapper.doSplitLogistics(0, 10, null).size());
        System.out.println("doSplitLogistics.size.search=" +logisticsMapper.doSplitLogistics(0, 10, "快递").size());
        System.out.println("doSearchLogistics"+logisticsMapper.doSearchLogistics("快递").size());
        System.out.println("doCounts.state1="+logisticsMapper.doCounts(1));
        System.out.println("doCounts.state0="+logisticsMapper.doCounts(0));
        System.out.println("doGetAllLogistics"+logisticsMapper.doGetAllLogistics().size());*/
        /*LogisticsRecord record = new LogisticsRecord(null,"1",1,new Date());
        System.out.println("doDeleteLogisticsRecord=" +recordMapper.doDeleteLogisticsRecord(record));
        System.out.println("doCreateLogisticsRecord="+recordMapper.doCreateLogisticsRecord(record));
        System.out.println("ByLogisticId="+recordMapper.doGetLogisticsRecordByLogisticId(1));
        System.out.println("ByRecordId="+recordMapper.doGetLogisticsRecordByRecordId("1"));
        System.out.println("doGetALlLogisticsRecords.size="+recordMapper.doGetALlLogisticsRecords().size());
        record.setCreateTime(new Date());
        record.setLogisticsId(2);
        System.out.println("doUpdateLogisticsRecord="+recordMapper.doUpdateLogisticsRecord(record));
        System.out.println("ByRecordId="+recordMapper.doGetLogisticsRecordByRecordId("1"));
        System.out.println("doDeleteLogisticsRecord="+recordMapper.doDeleteLogisticsRecord(record));*/
    }

    public void initCategory() {
        Integer r = 0;
        String data0 = "家用电器=平板电视：超薄电视，全面屏电视，智能电视，教育电视，OLED电视，智慧屏，4K超清电视，电视配件，55寸，65寸|" +
                "空调：空调挂机，空调柜机，中央空调，变频空调，一级能效，移动空调|" +
                "洗衣机：滚筒洗衣机，洗烘一体机，波轮洗衣机，迷你洗衣机，烘干机，洗衣机配件|" +
                "冰箱：多门，对开门，三门，双面，冷柜、冰吧，酒柜，冰箱配件|" +
                "厨卫大电：抽烟机，燃气灶，烟灶套装，集成灶，消毒柜，洗碗机，电热水器，燃气热水器，空气能热水器，太阳能热水器，嵌入式厨电，烟机灶具配件|" +
                "厨房小电：破壁机，电烤箱，电饭煲，电压力锅，电炖锅，豆浆机，料理机，咖啡机，电饼铛，榨汁机、原汁机，电水壶、热水瓶，微波炉，电火锅，养生壶，电磁炉，面包机，空气炸锅，面条机，电陶炉，电烧烤炉|" +
                "生活电器：电风扇，冷风扇，空气净化器，吸尘器，除螨仪扫地机器人，除湿机，干衣机，蒸汽拖把、拖地机，挂烫机、熨斗，电话机，饮水机，净水器，取暖电器，加湿器，毛球修剪器，生活电器配件|" +
                "个护健康：剃须刀，电动牙刷，电吹风，美容器，洁面仪，按摩器，健康秤，卷、直发器，剃/脱毛器，理发器，足浴盆，足疗机，按摩椅|" +
                "视听影音：家庭影院，KTV音箱，迷你音箱，DVD，供方，回音壁，麦克风" +
                "&电脑/办公=电脑整机：笔记本，游戏本，平板电脑，台式机，一体机，服务器/工作站|" +
                "电脑配件：显示器，CPU，主板，显卡，硬盘，内存，机箱，电影，电脑散热器，显示器支架，刻录机/光驱，声卡/扩展卡，装机配件，SSD固态硬盘，组装电脑，USB分线器，主板CPU套装|" +
                "外设产品：鼠标，键盘，键鼠套装，网络仪表仪器，U盘，移动硬盘，鼠标垫，摄像头，线缆，手写板，硬盘盒，电脑工具，电脑清洁，UPS电源，插座，平板电脑配件，笔记本配件|" +
                "游戏设备：游戏机，游戏耳机，手柄/方向盘，游戏软件，游戏周边|" +
                "网络产品：路由器，网络机顶盒，交换机，网络存储，网卡，5G/4G上网，网线，网络配件|" +
                "办公设备：摄影机，摄影配件，打印机，传真设备，验钞/点钞机，扫描设备，复合机，碎纸机，考勤门禁，会议音频视频，保险柜，装订/封装机，安防监控，白板|" +
                "文具：笔类，本册/便签，办公文具，文件收纳，学生文具，计算器，画具画材，财会用品，文房四宝|" +
                "耗材：硒鼓/墨粉，墨盒，色带，纸类，刻录光盘" +
                "&手机/运营商/数码=手机通讯：手机，游戏手机，5G手机，拍照手机，全面屏手机，老人机，对讲机，以旧换新，手机维修|" +
                "运营商：合约机，手机卡，宽带，充话费/流量，中国电信，中国移动，中国联通，挑靓号|" +
                "手机配件：手机壳，贴膜，手机存储卡，数据线，充电器，手机耳机，创意配件，手机饰品，手机电池，移动电源，蓝牙耳机，手机支架，车载配件，拍照配件|" +
                "摄影摄像：数码相机，微单相机，单反相机，拍立得，运动相机，摄像机，镜头，户外器材，影棚器材，冲印服务，数码相框|" +
                "数码配件：高速存储卡，三脚架/云台，相机包，滤镜，闪光灯/手柄，相机清洁/贴膜，机身附件，读卡器，支架，电池/充电器|" +
                "影音娱乐：海量存储卡，音箱/音箱，智能音箱，便携/无限音箱，收音机，麦高风，MP3/MP4，专业音频，音频线|" +
                "智能设备：智能手环，智能手表，智能眼镜，智能机器人，运动跟踪器，监控检测，智能配饰，智能家居，体感车，无人机|" +
                "电子教育：学生平板，点读机/笔,早教益智，录音笔，电纸书，电子词典，复读机，翻译机" +
                "&家居/家具/家装/厨具=厨具：水具酒具，烹饪锅具，炒锅，碗碟套装，厨房配件，刀剪菜板，锅具套装，茶具，咖啡具，保温杯，厨房置物架|" +
                "家访：四件套，被子，枕芯，毛巾浴巾，蚊帐，凉席，地毯地垫，床垫/床褥，毯子，抱枕靠垫，窗帘/窗纱，床单/床笠，被套，枕巾枕套，沙发点套，做点，桌布/罩件，蚕丝被，乳胶枕|" +
                "生活用品：收纳用品，雨伞语句，净化除味，浴室用品，洗晒/熨烫，缝纫/针织用品，保暖防护，清洁工具|" +
                "家装软饰：装饰字画，装饰摆件，手工/十字绣，相框/照片墙，墙贴/装饰贴，花瓶花艺，香薰蜡烛，节庆饰品，钟饰，帘饰隔断，创意家居，3D立体墙贴，玻璃贴纸，电视背景墙，电表箱装饰画|" +
                "灯具：吸顶灯，吊灯，台灯，筒灯射灯，庭院灯，装饰灯，LED灯，氛围照明，落地灯，应急灯/手电，节能灯|" +
                "家具：客厅，卧室，餐厅，书房，儿童，储物，办公家具，阳台户外，电脑桌，电视柜，茶几，办公柜，进口/原创，沙发，床，床垫，餐桌，衣柜，书架，鞋柜，置物架，电脑椅，晾衣架，儿童床，儿童桌椅，红木|" +
                "全屋定制：定制衣柜，榻榻米，橱柜，门，室内门，防盗门，窗，淋浴房，壁挂炉，散热器|" +
                "建筑材料：油漆涂料，涂刷辅料，瓷砖，地板，壁纸，强化地板，美缝剂，防水涂料，管材管件，木材/板材|" +
                "厨房卫浴：水槽，龙头，淋浴花洒，马桶，智能马桶，智能马桶盖，厨卫挂件，浴室柜，浴霸，集成吊顶，垃圾处理器|" +
                "五金电工：指纹锁，电动工具，手动工具，测量工具，工具箱，劳防用品，开关插座，配电箱/断路器，机械锁，拉手|" +
                "装修设计：全包装修，半包装修，家装设计，高端设计，局部装修，安装服务，装修公司，旧房翻新" +
                "&男装/女装/童装/内衣=女装：连衣裙，半身裙，T恤，衬衫，休闲裤，牛仔裤，短外套，卫衣，小西装，风衣，针织衫，雪纺衫，大码女装，中老年女装，短裤，正装裤，吊带/背心，打底衫，旗袍/汉服，礼服，婚纱，毛衣，羊绒衫，羽绒服，毛呢大衣，棉服，皮草，打底裤，加绒裤，设计师/潮牌|" +
                "男装：T恤(男)，牛仔裤(男)，休闲裤(男)，衬衫(男)，短裤(男)，POLO衫，羽绒服(男)，棉服(男)，夹克，卫衣(男)，真皮大衣(男)，毛呢大衣(男)，大码男装，西服套装，仿皮皮衣(男)，风衣(男)，针织衫(男)，马甲/背心，羊毛衫，羊绒衫(男)，西服，西裤，卫裤/运动裤，工装，设计师/潮牌(男)，唐装/中山装，中老年男装，加绒裤(男)|" +
                "内衣：文胸，睡衣/家居服，男士内裤，女士内裤，吊带、背心，文胸套装，情侣睡衣，塑身美体，少女文胸，休闲棉袜，商务男袜，连裤袜/丝袜，美腿袜，打底裤袜，抹胸，内衣配件，大码内衣，泳衣，秋衣秋裤，保暖内衣|" +
                "配饰：女士围巾/披肩，光学镜架/镜片，真皮手套，毛线帽，太阳镜，棒球帽，防辐射眼镜，男士丝巾/围巾，毛线手套，老花镜，领带/领结/领带夹，围巾/手套/帽子套装，鸭舌帽，毛线/布面料，贝雷帽，遮阳帽，礼帽，耳罩/耳包，遮阳伞/雨伞，袖扣，钥匙扣，游泳镜，男士腰带/礼盒，女士腰带/礼盒|" +
                "童装：儿童套装，卫衣(童)，裤子，外套/大衣，毛衣/针织衫，衬衫(童)，户外/运动服，T恤(童)，裙子，亲子装，礼服/演出服，羽绒服(童)，棉服(童)，内衣裤，家居服(童)，袜子(童)，民族服饰，连体衣/爬服|" +
                "童鞋：运动鞋(童)，靴子(童)，帆布鞋(童)，棉鞋，凉鞋(童)，拖鞋(童)" +
                "&美妆/个护清洁/宠物=面部护肤：美白，防晒，面膜，洁面，爽肤水，眼霜，乳液/面霜，卸妆，T区护理，润唇膏|" +
                "香水彩妆：隔离，遮瑕，气垫BB，粉底，腮红，口红/唇膏，唇釉/唇彩，睫毛膏，眼线，眼影，眉笔/眉粉，散粉，女士香水，男士香水，彩妆工具，男士彩妆，彩妆套餐|" +
                "男士护肤：控油，洁面(男)，乳液/面霜(男),面膜(男)，爽肤水(男)，剃须，精华，防晒(男)|" +
                "洗发护发：洗发水，护发素，发膜/精油，造型，染发，烫发，假发，美发工具，洗护套装|" +
                "口腔护理：牙膏，牙粉，牙贴，牙刷，漱口水，口喷，假牙清洁，口腔套装|" +
                "身体护理：花露水，沐浴露，香皂，洗手液，护手霜，浴盐，润肤，精油，美颈，美胸，纤体塑形，手膜/足膜，男士洗液，走珠/止汗露，脱毛刀/膏|" +
                "女性护理：卫生巾，卫生棉条，卫生护垫，私处护理|" +
                "纸品清洗：抽纸，卷纸，湿巾，厨房用纸，湿厕纸，洗衣凝珠，洗衣粉，肥皂，护理除菌|" +
                "家庭清洁：洗洁精，油污净，洁厕剂，消毒液，地板清洁剂，垃圾袋，垃圾桶，拖把/扫把，驱蚊驱虫|" +
                "宠物生活：狗粮，狗罐头，狗零食，狗厕所，尿垫，猫粮，猫罐头，猫零食，猫砂，猫砂盆，猫狗窝，食具水具，宠物玩具，宠物鞋服，小宠用品，水族世界，鱼缸/水族馆，鱼粮/私聊，水族活体" +
                "&女鞋/箱包/钟表/珠宝=时尚女鞋：休闲鞋，凉鞋，单鞋，拖鞋/人字拖，高跟鞋，帆布鞋，妈妈鞋，布鞋/绣花鞋，内增高，雨鞋/雨靴，女靴，雪地靴，鱼嘴鞋，马丁靴，踝靴，松糕鞋，坡跟鞋，鞋配件|" +
                "潮流女包：真皮包，单肩包，手提包，斜跨包，双肩包，手拿包，钱包(女)，卡包/零钱包，钥匙包|" +
                "潮流男包：男士钱包，男士双肩包，单肩/斜挎包，商务公文包，男士手包，卡包名片夹，钥匙包(男)|" +
                "功能箱包：拉杆箱，拉杆包，旅行包，电脑包，休闲运动包，书包，登山包，腰包/胸包，旅行配件|" +
                "奢侈品：箱包，钱包，服饰，腰带，鞋靴，太阳镜/眼镜框，饰品，配件|" +
                "钟表：DW，天梭，浪琴，欧米茄，肖邦，阿玛尼，卡西欧，西铁城，天王，瑞表，国表，日韩表，欧美表，德表，儿童手表，闹钟，挂钟，座钟，钟表配件|" +
                "珠宝首饰：黄金，K金，钻石，翡翠玉石，银饰，水晶玛瑙，彩宝，铂金，木手串/把件，珍珠，发饰" +
                "&男鞋/运动/户外=流行男鞋：休闲鞋(男)，凉鞋(男)，商务休闲鞋，正装鞋，拖鞋(男)，功能鞋，传统布鞋，增高鞋，男靴，雨鞋(男)，工装鞋|" +
                "运动鞋包：跑步鞋，篮球鞋，板鞋，运动包，足球鞋，专项运动鞋，训练鞋|" +
                "运动服饰：运动裤，卫衣/套头衫，健身服，运动背心，运动内衣|" +
                "健身训练：跑步机，动感单车，健身车，椭圆机，综合训练器，划船机，甩脂机，倒立机，踏步机，仰卧板/收腹机，瑜伽用品，舞蹈用品，运动护具，健腹轮，拉力器/臂力器，跳绳，引体向上器|" +
                "骑行运动：山地车，公路车，折叠车，骑行服，电动车，电动滑板车，城市自行车，平衡车，穿戴设备，自行车配件|" +
                "体育用品：兵乓球，羽毛球，篮球，足球，轮滑滑板，网球，高尔夫，台球，排球，棋牌麻将，民俗运动|" +
                "户外鞋服：户外风衣，徒步鞋，冲锋衣裤，速干衣裤，越野跑鞋，滑雪服，休闲衣裤，抓绒衣裤，溯溪鞋，沙滩/凉拖，软壳衣裤，功能内衣，军迷服饰，登山鞋，户外袜|" +
                "户外装备：背包，帐篷/垫子，望远镜，烧烤用具，便携桌椅床，户外配饰，军迷用品，野餐用品，睡袋/吊床，救援装备，户外照明，旅行装备，户外工具，户外仪表，登山攀岩，极限户外，冲浪潜水，滑雪装备|" +
                "垂钓用品：钓竿，鱼线，浮漂，鱼饵，钓鱼配件，渔具包，钓箱钓椅，鱼线轮，钓鱼灯，辅助装备|" +
                "游泳用品：女士泳衣，比基尼，男士泳衣，泳镜，游泳圈，游泳包防水包，泳帽，游泳配件" +
                "&房产/汽车/汽车用品=房产：普通住宅，别墅，商业办公，海外房产，文旅地产，长租公寓|" +
                "汽车车型：微型车，小型车，紧凑型车，中型车，中大型车，豪华车，MPV，SUV，跑车|" +
                "汽车价格：5-8万，8-12万，12-18万，25-40万，40万以上|" +
                "汽车品牌：大众，日产，丰田，别克，宝骏，本田|" +
                "维修保养：汽机油，轮胎，添加剂，防冻剂，滤清器，蓄电池，变速箱油/滤，雨刷，刹车片/盘，火花塞，车灯，轮毂，汽车玻璃，减震器，正时皮带汽车喇叭，改装配件，原厂件，底盘装甲/护板|" +
                "汽车装饰：坐垫座套，脚垫，头枕腰靠，方向盘套，后备箱垫，车载支架，车钥匙扣，香水，炭包/净化机，扶手箱，挂件摆件，车用收纳袋/盒，遮阳/雪挡，车衣，车贴，踏板，行李架/箱，雨眉，车装饰灯，车身饰件|" +
                "车载电器：行车记录仪，车载充电器，车机导航，车载蓝牙，智能驾驶，对讲电台，倒车雷达，导航仪，安全预警仪，车载净化器，车载吸尘器，汽车音响，车载冰箱，应急电源，逆变器，车载影音|" +
                "美容清洗：洗车机，洗车水枪，玻璃水，清洁剂，镀晶镀膜，车蜡，汽车贴膜，补漆笔，毛巾掸子|" +
                "安全自驾：胎压监测，充气泵，灭火器，车载床，应急救援，防盗设备，保温箱，储物箱|" +
                "汽车服务：洗车，钣金喷漆，清洗美容，改装服务，驾驶培训，油卡充值，加油卡" +
                "&母婴/玩具/乐器=奶粉：1段，2段，3段，4段，孕妈妈粉，特殊配方奶粉，有机奶粉|" +
                "营养辅食：米粉/菜粉，面条/粥，果泥/果汁，益生菌/初乳，DHA，钙铁锌/维生素，清火/开胃，宝宝零食|" +
                "尿裤湿巾：NB，S，M，L，XL，XXL，拉拉裤，成人尿裤，婴儿湿巾|" +
                "喂养用品：奶瓶奶嘴，吸奶器，暖奶消毒，辅食料理机，牙胶安抚，食物存储，儿童餐具，水壶/水杯，围兜/防溅衣|" +
                "洗护用品：宝宝护肤，日常护理，洗发沐浴，洗澡用具，洗衣液/皂，婴儿口腔清洁，座便器，驱蚊防晒|" +
                "寝居服饰：睡袋/抱被，婴儿枕，毛毯棉被，婴童床品，浴巾/浴衣，毛巾/口水巾，婴儿礼盒，婴儿内衣，婴儿外出服，隔尿垫巾，尿布，爬行垫，婴儿鞋帽袜|" +
                "妈妈专区：防辐射服，孕妈装，孕妇护肤，妈咪包/背婴带，待产护理，产后塑身，文胸/内裤，防溢乳垫，孕期营养|" +
                "童车童床：安全座椅，婴儿推车，婴儿床，婴儿床垫，餐椅，摇椅，学步车，三轮车，自行车，扭扭车，滑板车|" +
                "玩具：益智玩具，健身/戏水，橡皮泥，芭比娃娃，绘画/DIY，积木拼接，遥控/电动，毛绒玩具，娃娃玩具，动漫玩具，模型玩具，创意减压，科学实验玩具|" +
                "乐器：钢琴，电子琴，吉他，尤克里里，打击乐器，西洋管弦，民族乐器，乐器配件" +
                "&食品/酒类/生鲜/特产=新鲜水果：苹果，橙子，奇异果/猕猴桃，火龙果，榴莲，芒果，椰子，车厘子，百香果，柚子，国产水果，进口水果|" +
                "蔬菜蛋品：蛋品，叶菜类，根茎类，葱姜蒜椒，鲜菌菇，茄果瓜类，半加工蔬菜，半加工豆制品，玉米，山药，地瓜/红薯|" +
                "肉类：猪肉，牛肉，羊肉，鸡肉，鸭肉，冷鲜肉，特色肉类，内脏类，冷藏熟食，牛排，牛腩，鸡翅|" +
                "海鲜水产：鱼类，虾类，蟹类，贝类，鱿鱼/章鱼，活鲜，三文鱼，大闸蟹，小龙虾，海鲜加工品，海产干货|" +
                "冷饮冻食：水饺/混沌，汤圆/元宵，面点，烘焙半成品，奶酪/黄油，方便速食，火锅丸串，冰淇淋，冷藏饮料，低温奶，生日蛋糕|" +
                "中外名酒：白酒，葡萄酒，洋酒，啤酒，黄酒/养生酒，收藏酒/陈年老酒|" +
                "进口食品：牛奶，饼干蛋糕，糖/巧克力，零食，水，饮料，咖啡粉，冲调品，油，方便食品，米面调味|" +
                "休闲食品：中华老字号，营养零食，休闲零食，膨化食品，坚果炒货，肉感/熟食，蜜饯果干，糖果/巧克力，蛋糕饼干，月饼|" +
                "地方特产：北京，上海，新疆，陕西，湖南，云南，山东，江西，宿迁，成都，内蒙古，广西|" +
                "茗茶：铁观音，普洱，龙井，绿茶，红茶，乌龙茶，茉莉花茶，花草茶，花果茶，黑茶，白茶，养生茶，其他茶|" +
                "饮料冲调：冲调饮料，牛奶酸奶，饮用水，咖啡/奶茶，蜂产品/蜂蜜，冲饮谷物，成人奶粉|" +
                "粮油调味：大米，食用油，面，杂粮，调味品，南北干货，烘焙原料，有机食品" +
                "&艺术/礼品鲜花/农资绿植=艺术品：油画，版画，水墨画，雕塑，艺术画册，艺术衍生品|" +
                "火机烟具：打火机，一次性打火机，烟嘴，烟盒，烟斗|" +
                "礼品：创意礼品，电子礼品，工艺礼品，美妆礼品，婚庆节庆，礼盒礼券，礼盒定制，古董文玩，收藏品，礼品文具，熏香，京东卡，生日礼物|" +
                "鲜花速递：鲜花，每周一花，永生花，香皂花，卡通花束，干花|" +
                "绿植园艺：桌面绿植，苗木，绿植盆栽，多肉植物，花卉，种子种球，花盆花器，园艺土肥，园艺工具，园林机械|" +
                "种子：花草林木类，蔬菜/菌类，瓜果类，大田作物类|" +
                "农药：杀虫剂，杀菌剂，除草剂，植物生长调节剂|" +
                "肥料：氮/磷/钾肥，复合肥，生物菌肥，水溶/叶面肥，有机肥|" +
                "畜牧养殖：中兽药，西兽药，兽医器具，生产/运输器具，预混料，浓缩料，饲料添加剂，全价料，赶猪器/注射器，养殖场专用|" +
                "农机农具：微耕机，喷雾器，割草机，农机整机，农机配件，小型农机具，农膜" +
                "&医药保健/计生情趣=中西药品：感冒咳嗽，补肾壮阳，补气养血，止痛镇痛，耳鼻喉用药，眼科用药，口腔用药，皮肤用药，肠胃消化，风湿骨外伤，维生素/钙，男科/泌尿，妇科用药，儿科用药，心脑血管，避孕药，肝胆用药|" +
                "营养健康：增强免疫，骨骼健康，补肾强身，肠胃养护，调节三高，缓解疲劳，养肝1护肝，改善贫血，清咽利喉，美容养颜，减肥塑身，改善睡眠，明目益智|" +
                "营养成分：维生素/矿物质，胶原蛋白，益生菌，蛋白粉，玛咖，酵素，膳食纤维，叶酸，番茄红素，左旋肉碱|" +
                "滋补养生：阿胶，蜂蜜/蜂产品，枸杞，燕窝，冬虫夏草，人参/西洋参，三七，鹿茸，雪蛤，青钱柳，石斛/枫斗，灵芝/孢子粉，当归，养生茶饮，药食同源|" +
                "计生情趣：避孕套，排卵验孕，男用延时，飞机杯，倒模，仿真娃娃，震动棒，跳蛋，仿真阳具，情趣内衣|" +
                "保健器械：血压计，血糖计，心电仪，体温计，胎心仪，制氧机，呼吸机，雾化器，助听器，轮椅，拐杖，护理床，中医保健，养生器械，理疗仪，家庭护理，智能健康，出行常备|" +
                "护理护具：口罩，眼罩/耳塞，医用防疫用品，跌打损伤，创可贴，暖贴，鼻喉护理，眼部保健，美体护理|" +
                "隐形眼镜：彩色隐形，透明隐形，日抛，月抛，半年抛，护理液，润眼液，眼镜配件" +
                "&图书/文娱/教育/电子书=文学：小说，散文随笔，青春文学，传记，动漫，悬疑推理，科幻，武侠，世界名著|" +
                "童书：0-2岁，3-6岁，11-14岁，儿童文学，绘本，科普百科，幼儿启蒙，智力开发，少儿英语，玩具书|" +
                "教材教辅：教材，中小学教辅，考试，外语学习，字典词典，课外读物，英语四六级，会计类考试，国家公务员|" +
                "人文社科：历史，心理学，政治军事，传统文化，哲学宗教，社会科学，法律，文化，党政专区|" +
                "经管励志：管理，金融，经济，市场营销，领导力，股票，投资，励志与成功，自我完善|" +
                "艺术：绘画，摄影，书法，连环画，设计，建筑艺术，艺术史|" +
                "科学技术：计算机与互联网，科普，建筑，工业技术，电子通信，医学，科学与自然，农林|" +
                "生活：育儿家教，孕产胎教，健身保健，旅游地图，手工DIY，烹饪美食|" +
                "刊/原版：杂志/期刊，英文原版书，港台图书|" +
                "文娱音像：音乐，影视，教育音像，游戏，影视/动漫周边|" +
                "教育培训：青少年培训，语言培训，大学生培训，考试考证培训，职业技能培训|" +
                "电子书：电子小说，电子文学，经济管理，电子传记，电子社会科学，电子计算机与互联网，进口原版";
        //需要修改
        String[] object = data0.split("\\&");
        for (String obj : object) {
            String[] f_s_t = obj.split("\\=");//f-s-t[0]为一级分类，f-s-t[1]为所有二三级分类
            //创建一级分类
            GoodsCategory first = new GoodsCategory(null, f_s_t[0], null);
            goodsCategoryMapper.doCreateGoodsCategory(first);
            //获取一级分类id
            GoodsCategory firstCate = goodsCategoryMapper.doGetGoodsCategoryByName(f_s_t[0]);
            int parentIdF = firstCate.getCategoryId();
            String[] sts = f_s_t[1].split("\\|");//sts为每个二三级分类
//            System.out.println(Arrays.toString(sts));
            for (String st : sts) {
                String[] s_t = st.split("：");//s-t[0]为二级分类，s-t[1]为所有三级分类
                GoodsCategory second = new GoodsCategory(null, s_t[0], parentIdF);
                //创建二级分类
                goodsCategoryMapper.doCreateGoodsCategory(second);
                GoodsCategory secondCate = goodsCategoryMapper.doGetGoodsCategoryByName(s_t[0]);
                //二级分类的id
                Integer parentIdS = secondCate.getCategoryId();
                String[] t = s_t[1].split("，");//t为所有三级分类
                for (String th : t) {//遍历所有三级分类
                    //创建三级分类对象
                    GoodsCategory third = new GoodsCategory(null, th, parentIdS);
                    goodsCategoryMapper.doCreateGoodsCategory(third);
                }
            }
        }
    }

    public String initMemberOrder(){
        Integer i = 0;
        if (memberMapper.doGetMembers().size() > 10 && goodsMapper.doGetGoods().size() > 15){
            memberOrderMapper.doInitMemberOrder();//初始化用户订单信息
            List<Member> members = memberMapper.doSplitMember(0, 10, null,Constant.RECYCLE_STATE);
            List<Goods> goodsList = goodsMapper.doGetGoodsSplit(10, 3);
            Iterator<Member> miter = members.iterator();
            while (miter.hasNext()){//10个用户，每个用户五个订单。一个订单3个商品
                Member member = miter.next();
                String memberId = String.valueOf(member.getMemberId());
                List<String> orderList = new ArrayList<>();
                for (int x = 0 ; x < 5 ; x ++){
                    String orderId = String.valueOf(new Date().getTime() + x);
                    orderList.add(orderId);
                }
                Iterator<String> oiter = orderList.iterator();
                while (oiter.hasNext()){
                    String order = oiter.next();
                    Iterator<Goods> giter = goodsList.iterator();
                    while (giter.hasNext()){
                        Goods goods = giter.next();
                        String productId = goods.getProductId();
                        MemberOrder memberOrder = new MemberOrder(
                                null, order, memberId, productId, new Random().nextInt(3) +1
                        );
                        try {
                            Integer result = memberOrderMapper.doCreateMemberOrder(memberOrder);
                            if (result != 0){
                                i ++;
                            }
                        }catch (Exception e){
                            return "恢复用户订单出错，";
                        }
                    }
                }
            }
            return i + "条用户订单数据，";
        }
        return "用户信息和位置信息初始化失败导致用户订单恢复失败，";
    }

    public String  initOrder(){
        Integer i = 0;
        if (memberOrderMapper.doGetMemberOrders().size() > 0){
            orderMapper.doInitOrder();//初始化订单信息
            List<MemberOrder> memberOrders = memberOrderMapper.doGetMemberOrders();
            Set<String> ordersId = new HashSet<>();
            for (MemberOrder memberOrder : memberOrders) {
                ordersId.add(memberOrder.getOrderId());
            }
            for (String orderid : ordersId) {//遍历每一个订单id
                List<MemberOrder> orders = memberOrderMapper.doGetMemberOrderByoid(orderid);
                Double sum = 0.00;
                String uid = new String();
                for (MemberOrder order : orders) {//遍历每个订单的商品
                    Goods goods = goodsMapper.doGetGoodsById(order.getProductId());//取到对应的商品对象
                    Double price = goods.getPrice();//获取价格
                    Integer quantity = order.getQuantity();//获取每个商品的数量
                    sum += price*quantity; //计算总价
                    uid = order.getUserId();
                }
                Date createTime = new Date();
                Date payTime;
                Date closeTime;
                Date finishTime;
                Integer state = new Random().nextInt(5) + 1;
                if (state == 1){
                    payTime= new Date();
                    closeTime = null;
                    finishTime = new Date();
                }else if (state == 2){
                    payTime =new Date();
                    closeTime =null;
                    finishTime =null;
                }else if (state == 3){
                    payTime = null;
                    closeTime =null;
                    finishTime =null;
                }else if (state == 4){
                    payTime = null;
                    closeTime = new Date();
                    finishTime =null;
                }else {
                    payTime = null;
                    closeTime =null;
                    finishTime =null;
                }
                Order order = new Order(
                        orderid,
                        uid,
                        null,
                        sum,
                        null,
                        createTime,
                        payTime,
                        closeTime,
                        finishTime,
                        state
                );
                try {
                    Integer result = orderMapper.doCreateOrder(order);
                    if (result != 0){
                        i ++ ;
                    }
                }catch (Exception e){
                    return "恢复订单出错！";
                }
            }
            return i + "条订单数据!";
        }
        return "用户订单未初始化导致恢复订单失败！";
    }

    public Integer initAuthority(){
        Integer i = 0;
        if (authorityMapper.doCounts() > 1000){
            return 0;
        }else {
            String auth = new String(
                    "系统默认=/default|perms[default]|默认#商品管理=/goods/counts|perms[goods:counts]|分页数据统计," +
                            "/goods/lists|perms[goods:lists]|商品列表," +
                            "/goods/edit/page|perms[goods:edit:page]|进入商品编辑," +
                            "/goods/info|perms[goods:info]|获取编辑的商品信息," +
                            "/goods//edit|perms[goods:edit]|商品编辑," +
                            "/goods/add/page|perms[goods:add:page]|进入添加商品," +
                            "/goods/add|perms[goods:add]|添加商品," +
                            "/goods/dels|perms[goods:dels]|批量删除商品," +
                            "/goods/rels|perms[goods:rels]|批量发布商品," +
                            "/goods/offs|perms[goods:offs]|批量下架商品," +
                            "/goods/exams|perms[goods:exams]|批量审核商品," +
                            "/goods/release|perms[goods:release]|发布商品," +
                            "/goods/off|perms[goods:off]|下架商品," +
                            "/goods/examine|perms[goods:examine]|审核商品," +
                            "/goods/del|perms[goods:del]|删除商品#" +
                    "订单管理=/order/lists|perms[order:lists]|订单列表," +
                            "/order/dels|perms[order:dels]|批量删除订单," +
                            "/order/recs|perms[order:recs]|批量回收订单," +
                            "/order/recoveries|perms[order:recoveries]|批量恢复订单," +
                            "/order/del|perms[order:del]|删除订单," +
                            "/order/rec|perms[order:rec]|回收订单," +
                            "/order/deliver/page|perms[order:deliver:page]|进入订单发货," +
                            "/order/logistics/info|perms[order:logistics:info]|查看物流公司," +
                            "/order/deliver|perms[order:deliver]|订单发货," +
                            "/order/note|perms[order:note]|备注订单," +
                            "/order/note/info|perms[order:note:info]|查看订单备注," +
                            "/order/details|perms[order:details]|订单详情," +
                            "/order/recovery|perms[order:recovery]|恢复订单," +
                            "/order/recycle/bin|perms[order:recycle:bin]|查看订单回收站#" +
                    "会员管理=/member/lists|perms[member:lists]|会员列表," +
                            "/member/on|perms[member:on]|启用会员," +
                            "/member/off|perms[member:off]|停用会员," +
                            "/member/recy|perms[member:recy]|回收会员," +
                            "/member/recovery|perms[member:recovery]|恢复会员," +
                            "/member/del|perms[member:del]|删除会员," +
                            "/member/recys|perms[member:recys]|批量回收会员," +
                            "/member/recs|perms[member:recs]|批量恢复会员," +
                            "/member/dels|perms[member:dels]|批量删除会员," +
                            "/member/recycle/bin|perms[member:recycle:bin]|查看会员回收站#" +
                    "商品分类管理=/goods/cate/page|perms[goods:cate:page]|商品分类列表," +
                            "/goods/cate/top|perms[goods:cate:top]|获取顶级商品分类," +
                            "/goods/cate/child|perms[goods:cate:child]|获取子级商品分类," +
                            "/goods/cate/all|perms[goods:cate:all]|获取全部商品分类," +
                            "/goods/cate/edit|perms[goods:cate:edit]|编辑商品分类," +
                            "/goods/cate/del|perms[goods:cate:del]|删除商品分类#" +
                    "系统登录管理=/index|authc|进入首页," +
                            "/*|authc|进入系统," +
                            "/index.html|authc|进入系统首页," +
                            "/login|anon|系统登陆," +
                            "/unauth|authc|未授权页," +
                            "/commons/welc|authc|欢迎页#" +
                    "管理员管理=/admin/lists|perms[admin:lists]|管理员列表," +
                            "/admin/roles/lists|perms[admin:roles:lists]|查看管理员角色," +
                            "/admin/roles/has|perms[admin:roles:has]|查看管理员拥有的角色," +
                            "/admin/roles/info|perms[admin:roles:info]|获取编辑的角色信息," +
                            "/admin/roles/edit|perms[admin:roles:edit]|修改管理员角色," +
                            "/admin/perms|perms[admin:perms]|查看管理员权限信息," +
                            "/admin/on|perms[admin:on]|启用管理员," +
                            "/admin/off|perms[admin:off]|停用管理员," +
                            "/admin/del|perms[admin:del]|删除管理员," +
                            "/admin/update/page|perms[admin:update:page]|进入管理员编辑," +
                            "/admin/info|perms[admin:info]|获取编辑的管理员信息," +
                            "/admin/update|perms[admin:update]|更新管理员信息," +
                            "/admin/add/page|perms[admin:add:page]|进入管理员添加," +
                            "/admin/add|perms[admin:add]|添加管理员#" +
                    "角色管理=/role/lists|perms[role:lists]|查看角色列表," +
                            "/role/hasperms|perms[role:hasperms]|查看角色权限," +
                            "/role/add/page|perms[role:add:page]|进入添加角色," +
                            "/role/perms|perms[role:perms]|获取所有权限信息," +
                            "/role/add|perms[role:add]|添加角色," +
                            "/role/edit/page|perms[role:edit:page]|进入编辑角色," +
                            "/role/info|perms[role:info]|获取编辑角色信息," +
                            "/role/edit|perms[role:edit]|编辑角色," +
                            "/role/del|perms[role:del]|删除角色#" +
                    "权限管理=/authority/lists|perms[authority:lists]|权限列表," +
                            "/authority/edit/page|perms[authority:edit:page]|进入编辑权限," +
                            "/authority/info|perms[authority:info]|获取编辑的权限信息," +
                            "/authority/edit|perms[authority:edit]|编辑权限," +
                            "/authority/add/page|perms[authority:add:page]|进入添加权限," +
                            "/authority/g/info|perms[authority:g:info]|获取所有权限组信息," +
                            "/authority/add|perms[authority:add]|添加权限," +
                            "/authority/del|perms[authority:del]|删除权限," +
                            "/authority/group/lists|perms[authority:group:lists]|权限组列表," +
                            "/authority/group/edit/page|perms[authority:group:edit:page]|进入编辑权限组," +
                            "/authority/group/info|perms[authority:group:info]|获取编辑的权限组信息," +
                            "/authority/group/edit|perms[authority:group:edit]|编辑权限组," +
                            "/authority/group/add/page|perms[authority:group:add:page]|进入添加权限组," +
                            "/authority/group/add|perms[authority:group:add]|添加权限组," +
                            "/authority/group/del|perms[authority:group:del]|删除权限组#" +
                    "物流管理=/logistics/lists|perms[logistics:lists]|物流列表," +
                            "/logistics/add/page|perms[logistics:add:page]|前往物流添加," +
                            "/logistics/add|perms[logistics:add]|添加物流," +
                            "/logistics/edit/page|perms[logistics:edit:page]|前往物流编辑," +
                            "/logistics/info|perms[logistics:info]|查看编辑的物流信息," +
                            "/logistics/edit|perms[logistics:edit]|编辑物流," +
                            "/logistics/del|perms[logistics:del]|删除物流#" +
                    "系统设置=/system/run/infos|authc|系统运行信息#" +
                    "API接口管理=/api/swagger|authc|swagger接口文档," +
                            "/api/druid|authc|druid数据源监控接口"

            );
            String[] authGroups = auth.split("#");//每组权限信息
            for (String ag : authGroups) {
                String[] group_auth = ag.split("=");//权限组-其所有的权限
                String gname = group_auth[0];
                //创建权限组
                authGroupMapper.doCreateAuthGroup(gname);
                AuthGroup group = authGroupMapper.doGetGroupByName(gname);
                Integer groupId = group.getGroupId();
                String[] auths = group_auth[1].split(",");//权限
                for (String at : auths) {
                    String[] field_name_details = at.split("\\|");//字段-权限名-描述,注意“|”，要转义
                    Authority authority = new Authority(
                            9999,
                            field_name_details[1],
                            field_name_details[0],
                            groupId,
                            field_name_details[2]
                    );
                    //创建权限
                    Integer cnum = authorityMapper.doCreateAuthority(authority);
                    i += cnum;
                }
            }
            return i;
        }
    }
}
