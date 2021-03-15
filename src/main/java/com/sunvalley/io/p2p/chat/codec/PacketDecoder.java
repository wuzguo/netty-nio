package com.sunvalley.io.p2p.chat.codec;

import com.alibaba.fastjson.JSON;
import com.sunvalley.io.p2p.chat.entity.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 11:22
 */

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();

        if (in.readableBytes() < 4) {
            System.out.println("readableBytes length less than 4 bytes");
            in.resetReaderIndex();
            return;
        }

        int length = in.readInt();

        if (length < 0) {
            ctx.close();
            System.out.println("message length less than 0, channel closed");
            return;
        }

//        if (length > in.readableBytes() - 4) {
//            in.resetReaderIndex();
//            return;
//        }

        ByteBuf byteBuf = Unpooled.buffer(length);

        in.readBytes(byteBuf);

        try {
            byte[] body = byteBuf.array();
            BaseMessage message = JSON.parseObject(new String(body), BaseMessage.class);
            out.add(message);
            System.out.println(String.format("received Message length: %s, content: %s", length, JSON.toJSON(message)));
        } catch (Exception e) {
            System.out.println(ctx.channel().remoteAddress() + ",decode failed." + e.getMessage());
        }
    }
}
