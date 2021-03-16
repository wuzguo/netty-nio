package com.sunvalley.io.p2p.chat;

import com.sunvalley.io.p2p.chat.auth.AuthClient;
import com.sunvalley.io.p2p.chat.entity.Message;
import com.sunvalley.io.p2p.chat.entity.ResultMessage;
import com.sunvalley.io.p2p.chat.enums.TypeEnum;
import com.sunvalley.io.p2p.chat.utils.ChannelUtils;
import com.sunvalley.io.p2p.chat.utils.MessageUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Optional;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 16:29
 */

public class GateWayServerInboundHandler extends SimpleChannelInboundHandler<Message> {

    // 认证客户端
    private static final NettyClientPool authClientPool = AuthClient.getPool();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        // 返回消息直接写到客户端
        if (TypeEnum.RESULT.getValue().equals(message.getType())) {
            ResultMessage message1 = (ResultMessage) message.getMessage();
            Optional.ofNullable(ChannelUtils.getChannel(message1.getTo()))
                .ifPresent(channel -> channel.channel().writeAndFlush(message1));
            return;
        }

        // 记录通道消息
        ChannelUtils.addChannel(ctx);
        // 登录消息
        String content = String.valueOf(message.getMessage());
        if (content.contains("login")) {
            authClientPool.sendMessage(MessageUtils.toAuth(ctx.channel(), String.valueOf(message.getMessage())));
            return;
        }
        // 聊天消息
        authClientPool.sendMessage(MessageUtils.toBusiness(String.valueOf(message.getMessage())));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //    UserManager.online(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //    UserManager.offline(ctx.channel());
    }
}
