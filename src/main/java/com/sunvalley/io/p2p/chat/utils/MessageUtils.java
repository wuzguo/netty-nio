package com.sunvalley.io.p2p.chat.utils;

import com.sunvalley.io.p2p.chat.entity.AuthMessage;
import com.sunvalley.io.p2p.chat.entity.BusinessMessage;
import com.sunvalley.io.p2p.chat.entity.Message;
import com.sunvalley.io.p2p.chat.enums.TypeEnum;
import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 11:37
 */

@UtilityClass
public class MessageUtils {

    /**
     * 获取消息
     *
     * @param message 消息体
     * @return {@link Message}
     */
    public static Message to(@NonNull Object message, @NonNull Integer type) {
        return Message.builder().id(UUIDUtils.getId()).message(message).type(type).build();
    }

    /**
     * 获取消息
     *
     * @param message 消息体
     * @return {@link Message}
     */
    public static Message to(@NonNull Object message) {
        return Message.builder().id(UUIDUtils.getId()).message(message).build();
    }


    /**
     * 获取消息 格式：login xxx
     *
     * @param channel 消息体
     * @param content 昵称
     * @return {@link Message}
     */
    public static Message toAuth(@NonNull Channel channel, @NonNull String content) {
        int loginIndex = content.indexOf("login");
        String nickName = content.substring(loginIndex + 5);
        return MessageUtils.to(AuthMessage.builder().nickName(nickName.trim()).addr(channel.remoteAddress().toString())
            .channelId(channel.id()).build(), TypeEnum.AUTH.getValue());
    }

    /**
     * 获取消息 格式：from userid to userid say xxxxx
     *
     * @param content 消息体
     * @return {@link Message}
     */
    public static Message toBusiness(@NonNull String content) {
        int sayIndex = content.indexOf("say");
        int toIndex = content.indexOf("to");
        int formIndex = content.indexOf("from");
        String message = content.substring(sayIndex + 3);
        String to = content.substring(toIndex + 2, sayIndex);
        String from = content.substring(formIndex + 4, toIndex);
        if (StringUtil.isNullOrEmpty(message) || StringUtil.isNullOrEmpty(to) || StringUtil.isNullOrEmpty(from)) {
            System.out.println(String.format("消息格式无效： %s, %s, %s", from, to, message));
        }

        return MessageUtils
            .to(BusinessMessage.builder().from(from.trim()).to(to.trim()).message(message.trim()).type(1).build(),
                TypeEnum.BUSINESS.getValue());
    }
}
