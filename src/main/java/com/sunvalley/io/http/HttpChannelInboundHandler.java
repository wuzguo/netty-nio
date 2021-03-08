package com.sunvalley.io.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/4 15:51
 */

public class HttpChannelInboundHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext context, HttpObject object) throws Exception {
        if (object instanceof HttpRequest) {
            System.out.println("收到来自：" + context.channel().remoteAddress() + " 的消息");
            System.out.println("pipeline：" + context.pipeline().hashCode() + " channel：" + context.channel().hashCode());
            ByteBuf message = Unpooled.copiedBuffer("你好，我是服务器", CharsetUtil.UTF_8);
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, message);
            httpResponse.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
            httpResponse.headers().add(HttpHeaderNames.CONTENT_LENGTH, message.readableBytes());
            context.writeAndFlush(httpResponse);
        }
    }
}
