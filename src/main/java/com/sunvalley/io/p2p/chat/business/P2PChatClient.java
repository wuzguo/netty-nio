package com.sunvalley.io.p2p.chat.business;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 11:46
 */

public class P2PChatClient {

    private Channel channel;

    private Channel connect0(String hostName, Integer port) throws InterruptedException {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup(8);
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(loopGroup).channel(NioSocketChannel.class).handler(new P2PClientChannelInitializer());
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(hostName, port)).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println(String.format("连接服务器端口 %s 成功", port));
                }
            });
            return channelFuture.channel();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

    /**
     * 连接
     *
     * @param hostName 主机名称
     * @param port     端口号
     * @return {@link Channel}
     */
    public P2PChatClient(String hostName, Integer port) {
        try {
            this.channel = connect0(hostName, port);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * 发送消息
     *
     * @param message {@link Object}
     */
    public void writeAndFlush(@NonNull Object message) {
        this.channel.writeAndFlush(message);
    }
}
