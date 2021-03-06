package com.sunvalley.io.p2p.chat.business;

import com.sunvalley.io.netty.ExceptionHandler;
import com.sunvalley.io.p2p.chat.GateWayServerInboundHandler;
import com.sunvalley.io.p2p.chat.codec.PacketDecoder;
import com.sunvalley.io.p2p.chat.codec.PacketEncoder;
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

public class BusinessServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new PacketDecoder());
        pipeline.addLast("encoder", new PacketEncoder());
        pipeline.addLast("exceptionHandler", new ExceptionHandler());
        pipeline.addLast("businessInboundHandler", new BusinessServerInboundHandler());
    }
}
