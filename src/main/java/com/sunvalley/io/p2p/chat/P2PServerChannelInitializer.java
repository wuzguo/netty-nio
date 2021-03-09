package com.sunvalley.io.p2p.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 11:48
 */

public class P2PServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("serverAuthHandler", new ServerAuthHandler());
        pipeline.addLast("serverMessageHandler", new ServerMessageHandler());
        pipeline.addLast("idleStateHandler", new IdleStateHandler(2, 5, 10, TimeUnit.MINUTES));
        pipeline.addLast("idleStateEventHandler", new IdleStateEventHandler());
    }
}
