package cn.mikylin.boot.runner;

import cn.mikylin.boot.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 会在服务启动之后被立即执行
 *
 * @author mikylin
 * date 20200309
 */
@Component
public class NettyRunner implements CommandLineRunner {

    @Autowired
    NettyServer server;

    @Override
    public void run(String... args) throws Exception {
        server.start();
    }
}
