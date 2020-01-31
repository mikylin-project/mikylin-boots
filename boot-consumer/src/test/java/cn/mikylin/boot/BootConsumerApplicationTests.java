package cn.mikylin.boot;

import cn.mikylin.boot.dubbo.DubboUserConsumerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootConsumerApplicationTests {

    @Autowired
    DubboUserConsumerService consumer;

    @Test
    void test() {
        consumer.getUser(1L);
    }

}
