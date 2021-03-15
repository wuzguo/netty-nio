package com.sunvalley.io.p2p.chat;

import com.sunvalley.io.p2p.chat.codec.GatewayPacketDecoder;
import com.sunvalley.io.p2p.chat.codec.GatewayPacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 9:46
 */

public class GateWayServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new GatewayPacketDecoder());
        pipeline.addLast("encoder", new GatewayPacketEncoder());
        pipeline.addLast("gatewayServerHandler", new GateWayServerHandler());
    }
}
