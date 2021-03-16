package com.sunvalley.io.p2p.chat.business;

import com.sunvalley.io.p2p.chat.entity.BusinessMessage;
import com.sunvalley.io.p2p.chat.entity.Message;
import com.sunvalley.io.p2p.chat.entity.ResultMessage;
import com.sunvalley.io.p2p.chat.enums.TypeEnum;
import com.sunvalley.io.p2p.chat.utils.MessageUtils;
import com.sunvalley.io.p2p.chat.utils.UserChannelUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Optional;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 11:50
 */

public class BusinessServerInboundHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        BusinessMessage message1 = (BusinessMessage) message.getMessage();
        if (message1.getType() == 0) {
            ChannelId channel = message1.getChannelId();
            UserChannelUtils.addChannelIds(message1.getFrom(), channel);
            return;
        }

        // 发消息给某人
        Optional.ofNullable(UserChannelUtils.getChannelId(message1.getTo())).ifPresent(channel -> {
            ResultMessage resultMessage = ResultMessage.builder().type(TypeEnum.RESULT.getValue()).to(channel)
                .from(message1.getFrom()).message(message1.getMessage()).build();
            Message message2 = MessageUtils.to(resultMessage);
            message2.setType(TypeEnum.RESULT.getValue());
            ctx.writeAndFlush(message2);
        });
    }
}
