package com.sunvalley.io.p2p.chat.business;

import com.sunvalley.io.p2p.chat.entity.BaseMessage;
import com.sunvalley.io.p2p.chat.utils.UserManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Optional;
import lombok.NonNull;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 11:50
 */

public class BusinessServerInboundHandler extends SimpleChannelInboundHandler<BaseMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage message) throws Exception {
        System.out.println("business server message: " + message);

        BaseMessage baseMessage = BaseMessage.builder().id(message.getId()).type(message.getType())
            .message("你好，我是业务服务器").build();
        ctx.channel().writeAndFlush(baseMessage);

//        // 如果接收对象是空就群发消息
//        if (UserManager.getPartners(ctx.channel()).isEmpty()) {
//            this.sendGroupMessage(ctx.channel(), null);
//            return;
//        }
//
//        Optional.ofNullable(UserManager.getPartners(ctx.channel()))
//            .ifPresent(channels -> channels.forEach(channel -> channel.writeAndFlush(message)));
    }

    /**
     * 群发消息
     *
     * @param channel 通道
     * @param message 消息
     */
    private void sendGroupMessage(@NonNull Channel channel, @NonNull String message) {
        Optional.of(UserManager.getChannels()).ifPresent(mapChannels -> mapChannels.forEach((ch, user) -> {
            if (user.getStatus() && ch != channel) {
                ch.writeAndFlush(message);
            }
        }));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        sendGroupMessage(channel, channel.remoteAddress() + " 上线，大家欢迎");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        sendGroupMessage(channel, channel.remoteAddress() + " 离线，大家欢送");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        sendGroupMessage(channel, channel.remoteAddress() + " 加入群聊");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        sendGroupMessage(channel, channel.remoteAddress() + " 退出群聊");
    }
}
