package com.sunvalley.io.p2p.chat.auth;

import com.google.common.collect.Lists;
import com.sunvalley.io.p2p.chat.NettyClientPool;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 9:33
 */

public class AuthClient {

    /**
     * 主机名称
     */
    private String hostName;

    /**
     * 端口
     */
    private Integer port;


    public AuthClient(String hostName, Integer port) {
        this.hostName = hostName;
        this.port = port;
    }

    /**
     * 初始化
     */
    public void initialize() throws InterruptedException {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup(8);
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(loopGroup).channel(NioSocketChannel.class).handler(new AuthClientChannelInitializer());
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(this.hostName, this.port)).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接服务器端口 6666 成功");
                }
            });
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new AuthClient("127.0.0.1", 6670).initialize();
    }

    public static NettyClientPool getPool() {
        List<ChannelHandler> channelHandlers = Lists.newArrayList();
        channelHandlers.add(new AuthClientInboundHandler());
        return new NettyClientPool("127.0.0.1", 6670, channelHandlers);
    }
}
