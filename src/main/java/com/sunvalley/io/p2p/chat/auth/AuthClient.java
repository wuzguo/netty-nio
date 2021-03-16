package com.sunvalley.io.p2p.chat.auth;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sunvalley.io.p2p.chat.GateWayClientChannelInitializer;
import com.sunvalley.io.p2p.chat.GateWayClientInboundHandler;
import com.sunvalley.io.p2p.chat.NettyClientPool;
import com.sunvalley.io.p2p.chat.utils.MessageUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 9:33
 */

public class AuthClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup(8);
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(loopGroup).channel(NioSocketChannel.class).handler(new AuthClientChannelInitializer());
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 6670)).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接服务器端口 6666 成功");
                }
            });
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()) {
                channel.writeAndFlush(MessageUtils.to(scanner.nextLine()));
            }
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

    public static NettyClientPool getPool() {
        List<ChannelHandler> channelHandlers = Lists.newArrayList();
        channelHandlers.add(new AuthClientInboundHandler());
        return new NettyClientPool("127.0.0.1", 6670, channelHandlers);
    }
}
