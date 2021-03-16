package com.sunvalley.io.p2p.chat;

import com.google.common.collect.Lists;
import com.sunvalley.io.p2p.chat.utils.MessageUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Scanner;
import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 9:33
 */

@Data
public class GateWayClient {

    /**
     * 主机名称
     */
    private String hostName;

    /**
     * 端口
     */
    private Integer port;


    public GateWayClient(String hostName, Integer port) {
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
            bootstrap.group(loopGroup).channel(NioSocketChannel.class).handler(new GateWayClientChannelInitializer());
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(this.hostName, this.port)).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接服务器端口 6666 成功");
                }
            });
            Channel channel = channelFuture.channel();
            System.out.println(channel + " " + channel.remoteAddress());
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()) {
                channel.writeAndFlush(MessageUtils.to(scanner.nextLine()));
            }
        } finally {
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GateWayClient("127.0.0.1", 6666).initialize();
    }

    public static NettyClientPool getPool() {
        List<ChannelHandler> channelHandlers = Lists.newArrayList();
        channelHandlers.add(new GateWayClientInboundHandler());
        return new NettyClientPool("127.0.0.1", 6666, channelHandlers);
    }
}
