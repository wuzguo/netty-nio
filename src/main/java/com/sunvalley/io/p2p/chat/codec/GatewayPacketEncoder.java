package com.sunvalley.io.p2p.chat.codec;

import com.alibaba.fastjson.JSON;
import com.sunvalley.io.p2p.chat.entity.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 11:23
 */

public class GatewayPacketEncoder extends MessageToByteEncoder<BaseMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BaseMessage message, ByteBuf out) throws Exception {
        // 将对象转换为byte
        byte[] bytes = JSON.toJSONString(message).getBytes();
        int length = bytes.length;
        ByteBuf buf = Unpooled.buffer(8 + length);
        buf.writeInt(length);
        buf.writeBytes(bytes);
        out.writeBytes(buf);
        System.out.println(String.format("send message length: %s, content: %s", length, JSON.toJSONString(message)));
    }
}
