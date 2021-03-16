package com.sunvalley.io.p2p.chat.utils;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import java.util.Collection;
import java.util.Map;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/16 14:46
 */

public class UserChannelUtils {

    /**
     * 缓存Map
     */
    private static final Map<String, ChannelId> mapChannelIds = Maps.newConcurrentMap();


    /**
     * 增加Channel
     *
     * @param nickName 用户名
     * @param channel  通道
     */
    public static void addChannelIds(String nickName, ChannelId channel) {
        mapChannelIds.put(nickName, channel);
    }

    /**
     * 获取通道
     *
     * @param nickName 用户名
     * @return {@link Channel}
     */
    public static ChannelId getChannelId(String nickName) {
        return mapChannelIds.get(nickName);
    }

    /**
     * 移除通道
     *
     * @param nickName 用户名
     */
    public static void remove(String nickName) {
        mapChannelIds.remove(nickName);
    }

    public static Collection<ChannelId> getChannels() {
        return mapChannelIds.values();
    }
}
