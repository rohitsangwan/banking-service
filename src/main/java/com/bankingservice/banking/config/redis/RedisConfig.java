package com.bankingservice.banking.config.redis;

import com.bankingservice.banking.constants.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(Constants.REDIS_HOST);
        redisStandaloneConfiguration.setPort(Constants.REDIS_PORT);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public <T> RedisTemplate<String, T> objectRedisTemplate() {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());
        return redisTemplate;
    }
}
