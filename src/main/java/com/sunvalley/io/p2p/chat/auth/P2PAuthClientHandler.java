package com.sunvalley.io.p2p.chat.auth;

import com.sunvalley.io.p2p.chat.entity.BaseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 16:29
 */

public class P2PAuthClientHandler extends SimpleChannelInboundHandler<BaseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage message) throws Exception {
        System.out.println("auth client:" + message.toString());
    }
}