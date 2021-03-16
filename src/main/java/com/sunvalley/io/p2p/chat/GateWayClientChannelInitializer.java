package com.sunvalley.io.p2p.chat;

import com.sunvalley.io.netty.ExceptionHandler;
import com.sunvalley.io.p2p.chat.codec.PacketDecoder;
import com.sunvalley.io.p2p.chat.codec.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 9:45
 */

public class GateWayClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", new PacketEncoder());
        pipeline.addLast("exceptionHandler", new ExceptionHandler());
        pipeline.addLast("gateWayClientHandler", new GateWayClientInboundHandler());
    }
}
