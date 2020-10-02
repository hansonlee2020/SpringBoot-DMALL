package com.hanson.common.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: manager
 * @description: 缓存接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-27 13:28
 **/
public interface ICache<K,V> {

    //添加缓存
    public boolean addCache(Object target,Long expireTime, V value);

    //获取缓存所有的key信息
    public Set<K> getKeys();

    //获取缓存的某个value信息
    public V getValues(Object target);

    //检查缓存是否为空
    public boolean isCacheEmpty();

    //过期则清理缓存
    public void clearCacheIfExpire();

    //清理全部缓存
    public boolean clearAll();

    //自动清理缓存
    public void autoCacheClear(long expireTime,long lazyTime, TimeUnit timeUnit,long interval, String threadName);
}
