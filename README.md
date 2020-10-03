### 项目介绍

本项目的系统为网上商城后台信息管理系统，为网上商城提供数据维护和管理，实现服务和管理分离。项目使用J2EE三层结构+聚合工程，后台实现了运行日志记录以及在线接口文档。技术栈：SpringBoot、Mybatis、Shiro、Redis、MySQL、Layui、Swagger。

### 项目功能

- 后台登陆
  - 后台登陆：进行后台用户登陆身份的认证、授权
  - 后台登出：对登入系统的用户进行退出处理
- 后台管理
  - 商品管理：对商品信息进行管理，包括新增、修改、删除、搜索商品功能
  - 分类管理：管理和维护商品的分类信息
  - 订单管理：查询前台用户创建的订单信息，维护管理订单信息
  - 权限管理：管理后台系统的资源访问权限
  - 用户管理：维护前台系统的用户信息
  - 管理员管理：维护后台系统的用户信息和授权信息

项目功能模块


<img src="https://s1.ax1x.com/2020/06/29/NWCjTH.png" alt="image 20200629015658849" border="0">

### 技术介绍

#### 1、前端使用的技术

- [Layui](https://www.layui.com/)：前端模板框架
- [JQuery](https://jquery.com/)：jQuery是一个快速、小型且功能丰富的JavaScript库。它使用一个在多种浏览器上工作的易于使用的API，使得HTML文档遍历和操作、事件处理、动画和Ajax等工作变得更加简单。随着多功能性和可扩展性的结合，jQuery改变了数百万人编写JavaScript的方式。
- [AJAX](https://www.w3school.com.cn/ajax/index.asp)：全名Asynchronous JavaScript and XML，即异步的 JavaScript 和 XML，它是一种在不重新加载整个页面的情况下，实现与服务器交换数据并更新部分网页的技术。

#### 2、后端使用的技术

- [SpringBoot](https://spring.io/projects/spring-boot)：简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置。
- [Mybatis](https://mybatis.org/mybatis-3/)：是基于JDBC API的java持久层框架，支持自定义 SQL、存储过程以及高级映射，通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录
- [MySQL](https://www.mysql.com/)：关系型数据库，目前是Oracle旗下产品
- [Shiro](http://shiro.apache.org/)：Simple. Java. Security.（简单java安全框架），也是Apache的一个项目
- [Redis](https://redis.io/):即远程字典服务，是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。

### 项目目录结构

- dmall-common

- dmall-dao

- dmall-entity

- dmall-service

- dmall-utils

- dmall-web

  - main
    - java
    - resource
      - static
      - templates
      - application.yaml

  dmall.sql

  pom.xml

  README.md

  ENVIROMENET.txt

说明：

1. java目录存放的是系统的所有相关类，包括Controller层接口、aop、cache、config、exception
2. resource目录存放的是相关的配置文件，包括SpringBoot配置文件、Mybatis配置文件、log4j2日志配置文件，子目录static存放的是静态资源，templates存放的是系统页面文件
7. static/lib目录存放的是第三方组件
10. InitDataBase.sql文件是项目的数据库初始化文件，用于创建项目使用的数据库和各个数据表，包含了对数据表的详细说明
11. pom.xml是项目的所有依赖配置文件
12. ENVIROMENT.txt是项目运行和部署环境的说明文件
13. README.md是项目的介绍文档

### 项目部署

#### 1、Windows本地部署

- 下载zip直接解压或者使用git命令：git clone https://github.com/hansonlee2020/dreamMall.git (需要安装git)
- 安装JDK11、MySQL数据库[安装参考教程](https://www.cnblogs.com/BoKeYuan259/p/10966137.html)、Maven、IDEA开发工具、Redis
- 在IDEA中配置好JDK路径、MySQL路径
- 打开MySQL服务，执行InitDataBase.sql文件(可以安装MySQL可视化平台Navicat for MySQL[安装教程参考](https://www.cnblogs.com/runw/p/12255962.html)或者MySQL Workbench[安装教程参考](https://dev.mysql.com/downloads/workbench/)），也可以复制sql文件里的内容到控制台命令行直接执行(需要登陆到mysql服务[MySQL使用教程](https://www.cnblogs.com/shierlou-123/p/11207508.html))
- 如果MySQL数据库连接配置和项目的resource-->application.yaml的配置不一致，需要在该文件里进行修改，MySQL5.0及以下，驱动所在路径为com.mysql.jdbc.Driver，5.0版本以上为com.mysql.cj.jdbc.Driver，而且需要在url后面加上时区设置&serverTimezone=Asia/Shanghai，否则连接数据库时会出错
- 本项目采用了多环境配置，环境自行在application.yaml中选择，项目系统默认端口localhost:80,初始账号自行配置，可使用工具类的加密工具来生成账号密码，自行入库处理即可。

#### 2、Linux服务器部署

- 安装JDK11[教程参考](https://www.cnblogs.com/xiaoyiStudent/p/12250305.html)
- 安装MySQL[教程参考1](https://blog.csdn.net/qq_39170130/article/details/87938755)|[教程参考2](https://blog.csdn.net/weixin_39082031/article/details/105783765)
- 安装Redis
- 使用IDEA将项目打jar包[教程参考](https://www.bilibili.com/video/BV1ra4y1i7wi)
- 将jar包上传到Linux服务器上，上传文件使用应用[WinSCP](https://winscp.net/eng/docs/lang:chs)，远程连接Linux服务器使用[Putty](https://www.chiark.greenend.org.uk/~sgtatham/putty/)
- Linux下启动mysql
- 使用部署命令：java -jar jar包名，即可启动SpringBoot项目，如果要挂后台，使用部署命令：nohup java -jar jar包名 &
- 部署完成，访问项目形式：服务器公网ip:80/

### 项目地址

- 项目源码：https://github.com/hansonlee2020/SpringBoot-DMALL.git
- 项目演示：http://www.hansonlee.top
- 关于作者：https://github.com/hansonlee2020
