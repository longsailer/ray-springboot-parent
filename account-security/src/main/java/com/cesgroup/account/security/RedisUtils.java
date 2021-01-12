package org.ray.account.security;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: wenzhang.fu
 * @Date: 2019-07-02 13:18
 * @Description: redis
 */
@Service
public class RedisUtils {
	
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    public <T> void setInRedis(String key, T v) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        redisTemplate.opsForValue().set(key, v);
    }

    /**
     * 存储一定有效期的值
     *
     * @param key
     * @param v
     * @param timeout 【单位：秒】
     */
    public <T> void setInRedis(String key, T v, long timeout) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        redisTemplate.opsForValue().set(key, v, timeout, TimeUnit.SECONDS);
    }

    /**
     * 存储一定有效期的值
     *
     * @param key
     * @param v
     * @param timeout
     * @param unit    时间单位
     */
    public <T> void setInRedis(String key, T v, long timeout, TimeUnit unit) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        redisTemplate.opsForValue().set(key, v, timeout, unit);
    }

    /**
     * 获取指定类型的key的value
     *
     * @param key
     * @param c
     * @return
     */
    public <T> T getInRedis(String key, Class<T> c) {
        Object v = getInRedis(key);
        if (v != null) {
            return c.cast(v);
        }
        return null;
    }

    /**
     * 获取key对应的value
     *
     * @param key
     * @return
     */
    public Object getInRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除key对应的value
     *
     * @param key
     */
    public void deleteInRedis(String key) {
        redisTemplate.delete(key);
    }
}
