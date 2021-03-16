package com.sunvalley.io.p2p.chat.entity;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 10:16
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthMessage implements Serializable {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户地址
     */
    private String addr;

    /**
     * 端口号
     */
    private ChannelId channelId;
}
