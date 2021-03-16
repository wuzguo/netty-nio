package com.sunvalley.io.p2p.chat.utils;

import com.google.common.collect.Maps;
import com.sunvalley.io.p2p.chat.entity.AuthMessage;
import com.sunvalley.io.p2p.chat.entity.User;
import io.netty.channel.ChannelId;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 15:48
 */

@UtilityClass
public class UserManager {

    private static final ConcurrentMap<String, User> mapUsers = Maps.newConcurrentMap();


    /**
     * 添加用户
     *
     * @param message {@link AuthMessage} 消息
     */
    public static void add(@NonNull AuthMessage message) {
        User user = User.builder().id(IdUtils.idGen()).nickName(message.getNickName()).addr(message.getAddr())
            .channelId(message.getChannelId()).loginTime(LocalDateTime.now()).status(false).build();
        // 添加用户信息
        mapUsers.putIfAbsent(message.getNickName(), user);
    }

    /**
     * 判断用户是不是存在
     *
     * @param nickName 昵称
     * @return {@link Boolean} 不存在
     */
    public static Boolean contains(@NonNull String nickName, @NonNull ChannelId channelId) {
        User user = mapUsers.get(nickName);
        return !(Objects.isNull(user) || user.getChannelId().equals(channelId));
    }


    /**
     * 设置用户在线
     *
     * @param channelId 管道
     */
    public static void online(@NonNull ChannelId channelId) {
        mapUsers.forEach((nickName, user) -> {
            if (user.getChannelId().equals(channelId)) {
                user.setStatus(true);
            }
        });
    }

    /**
     * 设置用户离线
     *
     * @param channelId 管道
     */
    public static void offline(@NonNull ChannelId channelId) {
        mapUsers.forEach((nickName, user) -> {
            if (user.getChannelId().equals(channelId)) {
                user.setStatus(false);
            }
        });
    }

    /**
     * 移除
     *
     * @param channelId 管道
     */
    public static void remove(@NonNull ChannelId channelId) {
        for (Iterator<Entry<String, User>> it = mapUsers.entrySet().iterator(); it.hasNext(); ) {
            Entry<String, User> item = it.next();
            if (item.getValue().getChannelId() == channelId) {
                it.remove();
                break;
            }
        }
    }


    /**
     * 返回当前所有的通道
     *
     * @return {@link Map}
     */
    public static Collection<User> getUsers(Boolean status) {
        return mapUsers.values().stream().filter(user -> status.equals(user.getStatus())).collect(Collectors.toList());
    }
}
