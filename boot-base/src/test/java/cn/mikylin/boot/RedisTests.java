package cn.mikylin.boot;

import cn.mikylin.boot.common.redis.CommonRedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTests {

    @Autowired
    CommonRedisService stringRedis;

    @Test
    public void string() {
        stringRedis.set("haha","test");
    }


}
