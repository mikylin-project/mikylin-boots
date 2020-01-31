package cn.mikylin.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class BootProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootProviderApplication.class, args);
    }

}
