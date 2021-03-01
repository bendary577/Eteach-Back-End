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

    public void saveValue(String username, Object obj) {
        redisTemplate.opsForValue().set(username, obj.toString());
    }

    public synchronized Object getValue(String username) {
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate.opsForValue().get(username);
    }

    public void deleteValue(String username) {
        redisTemplate.opsForValue().getOperations().delete(username);
    }


}