package com.sunvalley.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/4 15:17
 */

public class NettyClient {

    public static void main(String[] args) {

        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new ClientChannelInitializer());

            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 6669)).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接服务器端口 6669 成功");
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
