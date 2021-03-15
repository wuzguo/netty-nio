package com.sunvalley.io.p2p.chat.auth;

import com.sunvalley.io.p2p.chat.entity.BaseMessage;
import com.sunvalley.io.p2p.chat.utils.UserManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 16:29
 */

public class P2PAuthServerHandler extends SimpleChannelInboundHandler<BaseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage message) throws Exception {
        Channel channel = ctx.channel();
        String addr = channel.remoteAddress().toString();
        Integer userId = UserManager.add(channel, addr);
        BaseMessage baseMessage = BaseMessage.builder().id(message.getId()).type(message.getType())
            .message("你好，你的用户ID 为" + userId).build();
        ctx.channel().writeAndFlush(baseMessage);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端：" + ctx.channel().remoteAddress() + "连接");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        //  UserManager.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //   UserManager.online(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //  UserManager.offline(ctx.channel());
    }
}
