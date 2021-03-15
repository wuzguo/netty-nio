package com.sunvalley.io.p2p.chat.utils;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
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
    private static final Map<String, Channel> mapChannel = Maps.newConcurrentMap();


    public static void addChannel(String id, Channel channel) {
        mapChannel.put(id, channel);
    }

    public static Channel getChannel(String id) {
        return mapChannel.get(id);
    }

    public static void remove(String id) {
        mapChannel.remove(id);
    }
}
