package com.sunvalley.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import java.util.concurrent.TimeUnit;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/4 15:10
 */

public class ServerChannelInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active ... " + ctx.name());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("收到来自 " + channel.remoteAddress() + " 的消息：" + buf.toString(CharsetUtil.UTF_8));

        ctx.channel().eventLoop().schedule(() -> {
            ByteBuf message = Unpooled.copiedBuffer("Hello, 你好...", CharsetUtil.UTF_8);
            ctx.writeAndFlush(message);
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ByteBuf message = Unpooled.copiedBuffer("Hello, 你好 客户端", CharsetUtil.UTF_8);
        ctx.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
