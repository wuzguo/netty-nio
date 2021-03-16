package com.sunvalley.io.p2p.chat.auth;

import com.sunvalley.io.p2p.chat.GateWayClient;
import com.sunvalley.io.p2p.chat.NettyClientPool;
import com.sunvalley.io.p2p.chat.entity.Message;
import com.sunvalley.io.p2p.chat.enums.TypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 16:29
 */

public class AuthClientInboundHandler extends SimpleChannelInboundHandler<Message> {

    // 认证客户端
    private static final NettyClientPool gateClientPool = GateWayClient.getPool();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        System.out.println("auth: " + message.toString());
        message.setType(TypeEnum.RESULT.getValue());
        gateClientPool.sendMessage(message);
    }
}
