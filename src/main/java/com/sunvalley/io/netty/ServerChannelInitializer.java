package com.sunvalley.io.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/4 15:04
 */

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("channelInboundHandler", new ServerChannelInboundHandler());
        pipeline.addFirst("channelOutboundHandler", new ServerChannelOutboundHandler());
        pipeline.addLast("exceptionHandler", new ExceptionHandler());
    }
}
