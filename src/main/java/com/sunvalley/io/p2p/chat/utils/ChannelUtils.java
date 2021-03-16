package com.sunvalley.io.p2p.chat.utils;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import java.util.Collection;
import java.util.Map;
import lombok.experimental.UtilityClass;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 10:44
 */

@UtilityClass
public class ChannelUtils {

    /**
     * 缓存Map
     */
    private static final Map<ChannelId, ChannelHandlerContext> mapChannels = Maps.newConcurrentMap();

    /**
     * 增加Channel
     *
     * @param context 通道
     */
    public static void addChannel(ChannelHandlerContext context) {
        mapChannels.put(context.channel().id(), context);
    }

    /**
     * 获取通道
     *
     * @param channelId ChannelId
     * @return {@link Channel}
     */
    public static ChannelHandlerContext getChannel(ChannelId channelId) {
        return mapChannels.get(channelId);
    }

    /**
     * 移除通道
     *
     * @param channelId ChannelId
     */
    public static void remove(ChannelId channelId) {
        mapChannels.remove(channelId);
    }

    public static Collection<ChannelHandlerContext> getChannels() {
        return mapChannels.values();
    }
}
