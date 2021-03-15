package com.sunvalley.io.p2p.chat.business;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 14:25
 */

public class IdleStateEventHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        Channel channel = ctx.channel();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            switch (stateEvent.state()) {
                case READER_IDLE:
                    System.out.println(channel.remoteAddress() +" 读空闲。服务器作出相应处理");
                    break;
                case WRITER_IDLE:
                    System.out.println(channel.remoteAddress() +" 写空闲。服务器作出相应处理");
                    break;
                case ALL_IDLE:
                    System.out.println(channel.remoteAddress() +" 读写空闲。服务器作出相应处理");
                    break;
                default:
                    System.out.println("无效的状态，服务器不知道怎么做");
            }
            channel.close();
        }
    }
}
