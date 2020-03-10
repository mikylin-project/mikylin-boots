package cn.mikylin.boot;

import cn.mikylin.boot.common.redis.StringRedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTests {

    @Autowired
    StringRedisService stringRedis;

    @Test
    public void string() {
        stringRedis.set("haha","test");
    }


}
