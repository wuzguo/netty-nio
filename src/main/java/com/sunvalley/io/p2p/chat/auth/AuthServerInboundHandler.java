package com.sunvalley.io.p2p.chat.auth;

import com.sunvalley.io.p2p.chat.NettyClientPool;
import com.sunvalley.io.p2p.chat.business.BusinessClient;
import com.sunvalley.io.p2p.chat.entity.AuthMessage;
import com.sunvalley.io.p2p.chat.entity.BusinessMessage;
import com.sunvalley.io.p2p.chat.entity.Message;
import com.sunvalley.io.p2p.chat.entity.ResultMessage;
import com.sunvalley.io.p2p.chat.entity.User;
import com.sunvalley.io.p2p.chat.enums.TypeEnum;
import com.sunvalley.io.p2p.chat.utils.UserManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;
import java.util.stream.Collectors;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 16:29
 */

public class AuthServerInboundHandler extends SimpleChannelInboundHandler<Message> {

    // 业务客户端
    private static final NettyClientPool businessPool = BusinessClient.getPool();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        if (message.getType().equals(TypeEnum.BUSINESS.getValue())) {
            businessPool.sendMessage(message);
            return;
        }

        AuthMessage authMessage = (AuthMessage) message.getMessage();
        if (UserManager.contains(authMessage.getNickName(), authMessage.getChannelId())) {
            ResultMessage message1 = ResultMessage.builder().to(authMessage.getChannelId())
                .message(String.format("你好，用户 %s 已存在", authMessage.getNickName())).build();
            Message baseMessage = Message.builder().id(message.getId()).type(TypeEnum.RESULT.getValue())
                .message(message1).build();
            ctx.channel().writeAndFlush(baseMessage);
            return;
        }

        String nikeNames = UserManager.getUsers(true).stream().map(User::getNickName).collect(Collectors.joining(", "));
        UserManager.add(authMessage);

        String content = StringUtil.isNullOrEmpty(nikeNames) ? "当前没其他用户在线" : String.format("当前在线用户有： %s", nikeNames);

        ResultMessage resultMessage = ResultMessage.builder().to(authMessage.getChannelId())
            .type(TypeEnum.RESULT.getValue()).message(String.format("你好，%s，%s", authMessage.getNickName(), content))
            .build();
        Message baseMessage = Message.builder().id(message.getId()).type(TypeEnum.RESULT.getValue())
            .message(resultMessage).build();
        BusinessMessage message1 = BusinessMessage.builder().from(authMessage.getNickName())
            .channelId(authMessage.getChannelId()).type(0).build();
        message.setMessage(message1);
        businessPool.sendMessage(message);
        ctx.channel().writeAndFlush(baseMessage);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        //
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        //  UserManager.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //   UserManager.online(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //  UserManager.offline(ctx.channel());
    }
}
