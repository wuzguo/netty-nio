package com.sunvalley.io.p2p.chat.utils;

import com.sunvalley.io.p2p.chat.entity.BaseMessage;
import com.sunvalley.io.p2p.chat.enums.TypeEnum;
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
     * @param content 消息体
     * @return {@link BaseMessage}
     */
    public static BaseMessage to(@NonNull String content) {
        return BaseMessage.builder().id(UUIDUtils.getId()).message(content.trim()).type(TypeEnum.AUTH.getValue())
            .build();
    }
}
