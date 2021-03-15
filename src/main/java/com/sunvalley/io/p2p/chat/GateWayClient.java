package com.sunvalley.io.p2p.chat;

import com.sunvalley.io.p2p.chat.utils.MessageUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
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

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup(8);
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(loopGroup).channel(NioSocketChannel.class)
                .handler(new GateWayClientChannelInitializer());
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 6666)).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接服务器端口 6666 成功");
                }
            });
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()) {
                channel.writeAndFlush(MessageUtils.to(scanner.nextLine() + "\r\n"));
            }
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
