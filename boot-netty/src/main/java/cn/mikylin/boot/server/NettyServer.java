package cn.mikylin.boot.server;

import cn.mikylin.boot.http.Http11ChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

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
            .childHandler(new Http11ChannelInitializer());

        try {
            strap.bind(port);
        } catch (Exception e) {
            log.error("Netty System error! e : {}",e);
        } finally {
            shutdown();
        }
    }


    @PreDestroy
    public void shutdown() {
        if(boss != null) {
            boss.shutdownGracefully();
        }
        if(worker != null) {
            worker.shutdownGracefully();
        }
    }
}
