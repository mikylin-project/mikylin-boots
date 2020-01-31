package cn.mikylin.boot;

import cn.mikylin.boot.kafka.KafkaProductor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootProviderApplicationTests {


    @Autowired
    KafkaProductor productor;

    @Test
    void kafka() {
        productor.send("123");
    }

}
