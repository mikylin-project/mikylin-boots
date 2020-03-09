package cn.mikylin.boot.server;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Netty Future 的监听器
 *
 * @author mikylin
 * @date 20200309
 */
public class NettyFutureListener implements GenericFutureListener<ChannelFuture> {

    /**
     * future 监听器
     * @param cf
     * @throws Exception
     */
    @Override
    public void operationComplete(ChannelFuture cf) throws Exception {
        if(cf.isSuccess()) {

        }
    }
}
