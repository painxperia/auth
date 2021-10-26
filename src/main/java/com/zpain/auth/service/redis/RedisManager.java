package com.zpain.auth.service.redis;

import java.time.Duration;

/**
 * @author zhangjun
 * @date 2021/7/5  16:21
 */
public interface RedisManager {

    /**
     * 设置数据
     *
     * @param key
     * @param t
     * @return
     */
    <T> Boolean setCache(String key, T t);

    /**
     * 设置数据
     *
     * @param key
     * @param hash
     * @param t
     * @return
     */
    <T> Boolean setHashCache(String key,String hash,T t);

    /**
     * 设置数据
     *
     * @param key
     * @param hash
     * @param t
     * @return
     */
    <T> Boolean setHashCacheDuration(String key,String hash,T t, Duration duration);

    /**
     * 过期时间插入
     *
     * @param key
     * @param t
     * @param duration
     * @param <T>
     * @return
     */
    <T> Boolean setCacheDuration(String key, T t, Duration duration);

    /**
     * 获取数据
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T getCache(String key);

    /**
     * 删除数据
     *
     * @param key
     * @return
     */
    Boolean deleteCache(String key);
}
