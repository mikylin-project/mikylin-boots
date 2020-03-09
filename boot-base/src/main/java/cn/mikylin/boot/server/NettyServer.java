package cn.mikylin.boot.server;

import groovy.util.logging.Slf4j;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NettyServer {

    private EventLoopGroup worker;
    private EventLoopGroup boss;

    @Value("${netty.port}")
    Integer port;

    @Value("${netty.workers}")
    Integer workers;


    public void start() {

        if(workers <= 0)
            workers = Runtime.getRuntime().availableProcessors();
        worker = new NioEventLoopGroup(workers);
        boss = new NioEventLoopGroup(1);

        ServerBootstrap strap = new ServerBootstrap();

        strap
            .group(boss,worker)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.DEBUG))
            .childHandler(new NettyChannelInitializer());

        try {
            strap
                .bind(port)
                .addListener(new NettyFutureListener());
        } finally {
            shutdown();
        }
    }


    private void shutdown() {
        if(boss != null) {
            boss.shutdownGracefully();
        }
        if(worker != null) {
            worker.shutdownGracefully();
        }
    }
}
