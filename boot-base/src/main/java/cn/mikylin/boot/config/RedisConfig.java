package cn.mikylin.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class RedisConfig {





    /**
     * 哨兵模式下的配置
     */
    @Value("#{'${spring.redis.sentinel.nodes}'.split(',')}")
    private List<String> nodes;
    @Value("${spring.redis.sentinel.master}")
    private String master;

//    @Autowired
//    RedisSentinelConfiguration sentinelConfiguration;

//    @Bean
//    @ConfigurationProperties(prefix="spring.redis")
//    public LettuceClientConfiguration getRedisConfig(){
//        LettuceClientConfiguration config = new LettuceClientConfiguration();
//        return config;
//    }

//    @Bean
//    public RedisSentinelConfiguration sentinelConfiguration(){
//        RedisSentinelConfiguration rs = new RedisSentinelConfiguration();
//        //配置matser的名称
//        rs.master("mymaster");
//        //配置redis的哨兵sentinel
//        Set<RedisNode> set = new HashSet<>();
//        nodes.forEach(x -> {
//            String[] split = x.split(":");
//            String host = split[0];
//            Integer port = Integer.parseInt(split[1]);
//            set.add(new RedisNode(host,port));
//        });
//        rs.setSentinels(set);
//        return rs;
//    }


    @Autowired
    private RedisConnectionFactory factory;


    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> rt = new RedisTemplate<>();
        rt.setConnectionFactory(factory);

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        // key 采用 String 的序列化方式
        rt.setKeySerializer(stringSerializer);
        // value 采用 String 的序列化方式
        rt.setValueSerializer(stringSerializer);
        // hash key 采用 String 的序列化方式
        rt.setHashKeySerializer(stringSerializer);
        // hash value 采用 String 的序列化方式
        rt.setHashValueSerializer(stringSerializer);

        return rt;
    }







}
