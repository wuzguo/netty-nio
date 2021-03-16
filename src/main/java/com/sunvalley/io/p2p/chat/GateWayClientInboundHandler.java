package com.sunvalley.io.p2p.chat;

import com.sunvalley.io.p2p.chat.entity.ResultMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 16:29
 */

public class GateWayClientInboundHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object message) throws Exception {
        // 如果是结果消息
        if (message instanceof ResultMessage) {
            ResultMessage message1 = (ResultMessage) message;
            System.out.println(String
                .format("%s 说: %s", StringUtil.isNullOrEmpty(message1.getFrom()) ? "系统" : message1.getFrom(),
                    message1.getMessage()));
        } else {
            System.out.println(message);
        }
    }
}
