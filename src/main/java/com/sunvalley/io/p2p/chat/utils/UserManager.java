package com.sunvalley.io.p2p.chat.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sunvalley.io.p2p.chat.entity.User;
import io.netty.channel.Channel;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
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

    // 记录当前在线的用户
    private static final ConcurrentMap<Channel, User> mapChannels = Maps.newConcurrentMap();

    // 配对
    private static final ConcurrentMap<Channel, List<Integer>> mapChannelPartner = Maps.newConcurrentMap();

    /**
     * 添加伙伴
     *
     * @param channel 通道
     * @param userId  用户ID
     */
    public static void addPartner(@NonNull Channel channel, @NonNull Integer userId) {
        mapChannelPartner.getOrDefault(channel, Lists.newArrayList()).add(userId);
    }

    /**
     * 获取当前用户的伙伴
     *
     * @param channel 通道
     * @return {@link List}
     */
    public List<Channel> getPartners(Channel channel) {
        List<Integer> userIds = mapChannelPartner.get(channel);
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Channel> channels = Lists.newArrayList();
        mapChannels.forEach((ch, user) -> {
            if (userIds.contains(user.getUid())) {
                channels.add(ch);
            }
        });
        return channels;
    }

    /**
     * 添加用户
     *
     * @param channel 通道
     * @param addr    地址
     * @return {@link Integer} 用户ID
     */
    public static Integer add(@NonNull Channel channel, @NonNull String addr) {
        Integer userId = IdUtils.idGen();
        User user = new User(addr, channel);
        mapChannels.putIfAbsent(channel, user);
        return userId;
    }

    /**
     * 设置用户在线
     *
     * @param channel 管道
     */
    public static void online(@NonNull Channel channel) {
        Optional.of(mapChannels.get(channel)).ifPresent(user -> {
            user.setLoginTime(LocalDateTime.now());
            user.setStatus(true);
        });
    }

    /**
     * 设置用户离线
     *
     * @param channel 管道
     */
    public static void offline(@NonNull Channel channel) {
        Optional.of(mapChannels.get(channel)).ifPresent(user -> user.setStatus(false));
    }

    /**
     * 移除
     *
     * @param channel 管道
     */
    public static void remove(@NonNull Channel channel) {
        Optional.of(mapChannels.get(channel)).ifPresent(user -> user.setStatus(false));
        mapChannels.remove(channel);
    }


    /**
     * 返回当前所有的通道
     *
     * @return {@link Map}
     */
    public static Map<Channel, User> getChannels() {
        return mapChannels;
    }
}
