package com.zpain.auth.service.redis.impl;

import com.zpain.auth.service.redis.RedisManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author zhangjun
 * @date 2021/7/5  16:22
 */
@Component
@Slf4j
public class RedisManagerImpl implements RedisManager {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <T> Boolean setCache(String key, T t) {
        Boolean b = false;
        try {
            redisTemplate.opsForValue().set(key, t);
            b = true;
        } catch (Exception e) {
            log.error("RedisManagerImpl[setCache] error:", e);
        }
        return b;
    }

    @Override
    public <T> Boolean setHashCache(String key, String hash, T t) {
        Boolean b = false;
        try {
            redisTemplate.opsForHash().put(key, hash, t);
            b = true;
        } catch (Exception e) {
            log.error("RedisManagerImpl[setCache] error:", e);
        }
        return b;
    }

    @Override
    public <T> Boolean setHashCacheDuration(String key, String hash, T t, Duration duration) {
        Boolean b = false;
        try {
            redisTemplate.opsForHash().put(key, hash, t);
            b = true;
        } catch (Exception e) {
            log.error("RedisManagerImpl[setCache] error:", e);
        }
        return b;
    }

    @Override
    public <T> Boolean setCacheDuration(String key, T t, Duration duration) {
        Boolean b = false;
        try {
            redisTemplate.opsForValue().set(key, t, duration);
            b = true;
        } catch (Exception e) {
            log.error("RedisManagerImpl[setCache] error:", e);
        }
        return b;
    }

    @Override
    public <T> T getCache(String key) {
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("RedisManagerImpl[getCache] error:", e);
            return null;
        }
    }

    @Override
    public Boolean deleteCache(String key) {
        Boolean b = false;
        try {
            b = redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("RedisManagerImpl[deleteCache] error:", e);
        }
        return b;
    }

}
