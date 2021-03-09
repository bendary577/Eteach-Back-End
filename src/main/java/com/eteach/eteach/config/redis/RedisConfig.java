package com.eteach.eteach.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
//@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${redis.pass}")
    private String redisPass;

    @Value("${redis.pool.max-active}")
    private Integer redisPoolMaxActive;

    @Value("${redis.pool.max-idle}")
    private Integer redisPoolMaxIdle;

    @Bean
    @Primary
    JedisConnectionFactory jedisConnectionFactory() {
        System.out.println("redis config 1");
        JedisConnectionFactory factory = new JedisConnectionFactory();
        System.out.println("redis config 2");
        factory.setHostName(redisHost);
        factory.setPort(redisPort);
        System.out.println("redis config 3");
        if (redisPass != null) {
            factory.setPassword(redisPass);
        }
        factory.setUsePool(true);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisPoolMaxActive);
        jedisPoolConfig.setMaxIdle(redisPoolMaxIdle);
        factory.setPoolConfig(jedisPoolConfig);
        System.out.println("redis config 4");
        return factory;
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() throws Exception {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        System.out.println("redis config 5");
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        System.out.println("redis config 6");
        return template;
    }

}
