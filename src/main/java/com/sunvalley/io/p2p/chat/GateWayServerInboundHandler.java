package com.sunvalley.io.p2p.chat;

import com.sunvalley.io.p2p.chat.auth.AuthClient;
import com.sunvalley.io.p2p.chat.business.BusinessClient;
import com.sunvalley.io.p2p.chat.entity.BaseMessage;
import com.sunvalley.io.p2p.chat.enums.TypeEnum;
import com.sunvalley.io.p2p.chat.utils.ChannelUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 16:29
 */

public class GateWayServerInboundHandler extends SimpleChannelInboundHandler<BaseMessage> {

    // 认证客户端
    private static final NettyClientPool authClientPool = AuthClient.getPool();

    // 业务客户端
    private static final NettyClientPool businessClientPool = BusinessClient.getPool();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage message) throws Exception {
        System.out.println(message);
        if (message.getType().equals(TypeEnum.RESULT.getValue())) {
            ChannelUtils.getChannel(message.getId()).writeAndFlush(message);
        } else if (message.getType().equals(TypeEnum.BUSINESS.getValue())) {
            ChannelUtils.addChannel(message.getId(), ctx.channel());
            businessClientPool.sendMessage(message);
        } else {
            ChannelUtils.addChannel(message.getId(), ctx.channel());
            authClientPool.sendMessage(message);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        //    UserManager.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //    UserManager.online(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //    UserManager.offline(ctx.channel());
    }
}
