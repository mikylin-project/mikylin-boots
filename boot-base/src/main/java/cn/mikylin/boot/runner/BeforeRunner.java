package cn.mikylin.boot.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * ApplicationRunner 会在服务启动之后被立即执行，主要用来做一些初始化的工作。
 * 但是该方法的运行是在 SpringApplication.run(…​) 执行完毕之前执行
 *
 * @author mikylin
 * date 20200309
 */
@Component
public class BeforeRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
