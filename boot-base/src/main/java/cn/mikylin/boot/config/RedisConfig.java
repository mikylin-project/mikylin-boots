package cn.mikylin.boot.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Redis 配置
 *
 * @author mikylin
 * @date 20200309
 */
@Configuration
public class RedisConfig {

    private static RedisTemplate<String,Object> rt(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> rt = new RedisTemplate<>();
        rt.setConnectionFactory(factory);

        RedisSerializer<String> srs = new StringRedisSerializer();
        // key 采用 String 的序列化方式
        rt.setKeySerializer(srs);
        // value 采用 String 的序列化方式
        rt.setValueSerializer(srs);
        // hash key 采用 String 的序列化方式
        rt.setHashKeySerializer(srs);
        // hash value 采用 String 的序列化方式
        rt.setHashValueSerializer(srs);

        return rt;
    }

    @Configuration
    @ConditionalOnProperty(name = "host",prefix = "redis.redis-1")
    public static class RedisOneConfig {

        @Value("${redis.redis-1.host}")
        private String host;
        @Value("${redis.redis-1.port}")
        private Integer port;
        @Value("${redis.redis-1.password}")
        private String password;
        @Value("${redis.redis-1.database}")
        private Integer database;
        @Value("${redis.redis-1.lettuce.pool.max-active}")
        private Integer maxActive;
        @Value("${redis.redis-1.lettuce.pool.max-idle}")
        private Integer maxIdle;
        @Value("${redis.redis-1.lettuce.pool.max-wait}")
        private Long maxWait;
        @Value("${redis.redis-1.lettuce.pool.min-idle}")
        private Integer minIdle;

        @Bean(name = "redisOneFactory")
        public RedisConnectionFactory redisOneFactory() {
            // 连接池
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMaxTotal(maxActive);
            poolConfig.setMaxIdle(maxIdle);
            poolConfig.setMinIdle(minIdle);
            poolConfig.setMaxWaitMillis(maxWait);

            // 配置文件
            RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
            redisConfig.setHostName(host);
            redisConfig.setPassword(RedisPassword.of(password));
            redisConfig.setPort(port);
            redisConfig.setDatabase(database);

            LettuceClientConfiguration clientConfig =
                    LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(100))
                            .poolConfig(poolConfig).build();
            return new LettuceConnectionFactory(redisConfig, clientConfig);
        }

        @Bean(name = "redisTemplate-1")
        public RedisTemplate<String,Object> redisTemplateOne(
                @Autowired @Qualifier("redisOneFactory")
                        RedisConnectionFactory factory) {
            return rt(factory);
        }
    }


    @Configuration
    @ConditionalOnProperty(name = "host",prefix = "redis.redis-2")
    public static class RedisTwoConfig {

        @Value("${redis.redis-2.host}")
        private String host;
        @Value("${redis.redis-2.port}")
        private Integer port;
        @Value("${redis.redis-2.password}")
        private String password;
        @Value("${redis.redis-2.database}")
        private Integer database;
        @Value("${redis.redis-2.lettuce.pool.max-active}")
        private Integer maxActive;
        @Value("${redis.redis-2.lettuce.pool.max-idle}")
        private Integer maxIdle;
        @Value("${redis.redis-2.lettuce.pool.max-wait}")
        private Long maxWait;
        @Value("${redis.redis-2.lettuce.pool.min-idle}")
        private Integer minIdle;

        @Bean(name = "redisTwoFactory")
        public RedisConnectionFactory redisTwoFactory() {
            // 连接池
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMaxTotal(maxActive);
            poolConfig.setMaxIdle(maxIdle);
            poolConfig.setMinIdle(minIdle);
            poolConfig.setMaxWaitMillis(maxWait);

            // 配置文件
            RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
            redisConfig.setHostName(host);
            redisConfig.setPassword(RedisPassword.of(password));
            redisConfig.setPort(port);
            redisConfig.setDatabase(database);

            LettuceClientConfiguration clientConfig =
                    LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(100))
                            .poolConfig(poolConfig).build();
            return new LettuceConnectionFactory(redisConfig, clientConfig);
        }

        @Bean(name = "redisTemplate-2")
        public RedisTemplate<String,Object> redisTemplateTwo(
                @Autowired @Qualifier("redisTwoFactory")
                        RedisConnectionFactory factory) {
            return rt(factory);
        }
    }

}
