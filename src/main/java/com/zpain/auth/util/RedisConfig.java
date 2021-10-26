package com.zpain.auth.util;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * @author zhangjun
 * @date 2021/7/5  16:42
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.database}")
    private int dataBase;

    @Value("${spring.redis.port}")
    private int port;

    public GenericObjectPoolConfig<Object> genericObjectPoolConfig() {
        GenericObjectPoolConfig<Object> configPool = new GenericObjectPoolConfig<>();
        configPool.setMaxIdle(80);
        configPool.setMaxTotal(100);
        configPool.setMinIdle(10);
        configPool.setMaxWaitMillis(10000L);
        return configPool;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisStandaloneConfiguration poolConfig = new RedisStandaloneConfiguration();
        poolConfig.setDatabase(dataBase);
        poolConfig.setHostName(host);
        poolConfig.setPort(port);
        LettucePoolingClientConfiguration lpc = LettucePoolingClientConfiguration.builder().poolConfig(genericObjectPoolConfig())
                .commandTimeout(Duration.ofMillis(1000)).build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(poolConfig, lpc);
        factory.afterPropertiesSet();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(genericFastJsonRedisSerializer);
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(genericFastJsonRedisSerializer);
        return redisTemplate;
    }

//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redissonClient() throws IOException {
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://" + host + ":" + port);
//        return Redisson.create(config);
//    }
}
