package com.brookezb.bhs.utils;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author brooke_zb
 */
@Service
@SuppressWarnings("unchecked")
public class RedisUtils {
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisUtils(StringRedisTemplate stringRedisTemplate, RedisConnectionFactory factory) {
        this.stringRedisTemplate = stringRedisTemplate;

        this.redisTemplate = new RedisTemplate<>();
        this.redisTemplate.setConnectionFactory(factory);
        this.redisTemplate.setKeySerializer(RedisSerializer.string());
        this.redisTemplate.setValueSerializer(RedisSerializer.json());
        this.redisTemplate.setHashKeySerializer(RedisSerializer.string());
        this.redisTemplate.setHashValueSerializer(RedisSerializer.json());
        this.redisTemplate.afterPropertiesSet();
    }

    public void setStringValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void setStringValue(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public void setObjectValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setObjectValue(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public String getStringValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public <T> T getObjectValue(String key, Class<T> clazz) {
        Object object = redisTemplate.opsForValue().get(key);
        if (object == null) {
            return null;
        }
        return (T) redisTemplate.opsForValue().get(key);
    }

    public Boolean exist(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }
}
