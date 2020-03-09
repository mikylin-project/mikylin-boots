//package cn.mikylin.boot.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisNode;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * Reids 哨兵模式下的配置
// *
// * @author mikylin
// * @date 20200309
// */
//public class RedisSentinelConfig {
//
//    @Value("#{'${spring.redis.sentinel.nodes}'.split(',')}")
//    private List<String> nodes;
//    @Value("${spring.redis.sentinel.master}")
//    private String master;
//
//    @Autowired
//    RedisSentinelConfiguration sentinelConfiguration;
//
//
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
//}
