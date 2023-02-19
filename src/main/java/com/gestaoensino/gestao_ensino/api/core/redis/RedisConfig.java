package com.gestaoensino.gestao_ensino.api.core.redis;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.util.StringUtils;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${redis.host}")
    String redisHost;

    @Value("${redis.port}")
    int redisPort;

    @Value("${redis.database.index}")
    int redisDatabase;

    @Value("${redis.password}")
    String redisPassword;


    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.defaultConfiguration();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost,
                redisPort);

        if (!StringUtils.isEmpty(redisPassword)) {
            jedisClientConfiguration = JedisClientConfiguration.builder().useSsl().build();
            redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
        }
        redisStandaloneConfiguration.setDatabase(redisDatabase);
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }


}
