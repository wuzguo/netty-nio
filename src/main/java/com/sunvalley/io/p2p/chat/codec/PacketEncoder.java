package com.sunvalley.io.p2p.chat.codec;

import com.sunvalley.io.p2p.chat.utils.ObjectUtils;
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

public class PacketEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object message, ByteBuf out) throws Exception {
        // 将对象转换为byte
        byte[] bytes = ObjectUtils.objectToBytes(message);
        int length = bytes.length;
        ByteBuf buf = Unpooled.buffer(8 + length);
        buf.writeInt(length);
        buf.writeBytes(bytes);
        out.writeBytes(buf);
    }
}
