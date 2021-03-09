package com.sunvalley.io.p2p.chat;

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

public class ServerAuthHandler extends SimpleChannelInboundHandler<Integer> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Integer useId) throws Exception {
        UserManager.addPartner(ctx.channel(), useId);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String addr = channel.remoteAddress().toString();
        Integer userId = UserManager.add(channel, addr);
        ctx.channel().writeAndFlush("你好，" + addr + ", 你的用户ID: " + userId);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        UserManager.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserManager.online(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        UserManager.offline(ctx.channel());
    }
}
