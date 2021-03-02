package com.eteach.eteach.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisService {

    private RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void saveValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value.toString());
    }

    public synchronized Object getValue(String key) {
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }

    public boolean isAvailable(String key){
        return !(redisTemplate.opsForValue().get(key) == null);
    }

}