package com.hanson.config;

import com.hanson.mapper.AuthorityMapper;
import com.hanson.pojo.vo.Authority;
import com.hanson.service.ShiroService;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.time.Duration;
import java.util.*;

/**
 * @program: manager
 * @description: shiro配置
 * @param:
 * @author: Hanson
 * @create: 2020-09-04 18:35
 **/
@Configuration
public class ShiroConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private int database;
    private int expire = 180 * 1000;//3分钟过期

    //配置凭证匹配器
    @Bean(value = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);
        return credentialsMatcher;
    }
    //配置realm
    @Bean(value = "dmUserRealm")
    public DMUserRealm dmUserRealm(@Qualifier("credentialsMatcher")CredentialsMatcher credentialsMatcher){
        DMUserRealm userRealm = new DMUserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }
    //配置安全管理器
    @Bean(value = "dwSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("dmUserRealm") DMUserRealm userRealm,
                                                               @Qualifier("defaultWebSessionManager")DefaultWebSessionManager defaultWebSessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(defaultWebSessionManager);
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }
    //配置shiro过滤器 先不配置
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("dwSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager,
                                                         @Qualifier("dmLogoutFilter") DMLogoutFilter logoutFilter,
                                                         @Qualifier("permissionFilter") PermissionFilter permissionFilter,
                                                         ShiroService shiroService){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        //指定登陆页面
        bean.setLoginUrl("/login");
        //指定未授权页面
        bean.setUnauthorizedUrl("/unauth");
        //认证成功页面
        bean.setSuccessUrl("/");
        //配置过滤链
        Map<String, String> filterChainDefinitionMap = shiroService.loadFilterChainDefinitions();
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //配置自定义过滤器
        Map<String, Filter> filters = new HashMap<>();
        filters.put("logout",logoutFilter);
        filters.put("perms",permissionFilter);//等写了授权模块再配置
        bean.setFilters(filters);

        return bean;
    }
    //配置登出过滤器
    @Bean(value = "dmLogoutFilter")
    public DMLogoutFilter dmLogoutFilter(){
        DMLogoutFilter logoutFilter = new DMLogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        return logoutFilter;
    }
    //授权拦截器
    @Bean(value = "permissionFilter")
    public PermissionFilter permissionFilter(){
        return new PermissionFilter();
    }



    /*
     * Shiro生命周期处理器
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /*
     * 开启Shiro的注解
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        //指定强制使用cglib为action创建代理对象
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /*
    * 开启shiro aop注解支持. 使用代理方式;
    */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("dwSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(value = "shiroSessionListener")
    public ShiroSessionListener sessionListener(){
        return new ShiroSessionListener();
    }

    //配置会话ID生成器
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /*
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        //使用redisCacheManager
        enterpriseCacheSessionDAO.setCacheManager(redisCacheManager());
        //设置session缓存的名字 默认为 shiro-activeSessionCache
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        //sessionId生成器
        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return enterpriseCacheSessionDAO;
    }

    /*
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * @return
     */
    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("SESSIONID");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
//        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    //定义session manager
    @Bean(value = "defaultWebSessionManager")
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setCacheManager(redisCacheManager());
        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        sessionManager.setGlobalSessionTimeout(1800 * 1000);//1800000
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
        sessionManager.setSessionValidationInterval(3600000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    //RedisSessionDAO shiro sessionDao层的实现 通过redis使用的是shiro-redis开源插件
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    //redisManager
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        redisManager.setDatabase(database);
        return redisManager;
    }
    //redisCacheManager
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setPrincipalIdFieldName("userId");
        redisCacheManager.setExpire(expire);
        return redisCacheManager;
    }
}
