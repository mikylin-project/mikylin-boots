package cn.mikylin.boot.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    /**
     * 均采用 string 方式获取 redis template
     */
    private static StringRedisTemplate srt(RedisConnectionFactory factory) {
        StringRedisTemplate srt = new StringRedisTemplate();
        srt.setConnectionFactory(factory);
        return srt;
    }

    /**
     * 使用自定义的方式获取 redis template，支持多种序列化手段
     */
    private static RedisTemplate<String,Object> rt(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> rt = new RedisTemplate<>();
        rt.setConnectionFactory(factory);

        // RedisSerializer<String> srs = new StringRedisSerializer();
        RedisSerializer<String> srs = RedisSerializer.string(); // utf-8 编码的序列化实例对象
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
    @ConditionalOnProperty(name = "host",prefix = "redis.redis")
    public static class RedisOneConfig {

        @Value("${redis.redis.host}")
        private String host;
        @Value("${redis.redis.port}")
        private Integer port;
        @Value("${redis.redis.password}")
        private String password;
        @Value("${redis.redis.database}")
        private Integer database;
        @Value("${redis.redis.lettuce.pool.max-active}")
        private Integer maxActive;
        @Value("${redis.redis.lettuce.pool.max-idle}")
        private Integer maxIdle;
        @Value("${redis.redis.lettuce.pool.max-wait}")
        private Long maxWait;
        @Value("${redis.redis.lettuce.pool.min-idle}")
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

        @Bean(name = "redisTemplate")
        @Primary
        public StringRedisTemplate redisTemplateOne(
                @Autowired @Qualifier("redisOneFactory")
                        RedisConnectionFactory factory) {
            return srt(factory);
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
