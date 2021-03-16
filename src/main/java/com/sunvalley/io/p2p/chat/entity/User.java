package com.sunvalley.io.p2p.chat.entity;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 15:48
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * 是否在线
     */
    private Boolean status = false;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginTime;

    /**
     * UID
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 地址
     */
    private String addr;

    /**
     * 通道
     */
    private ChannelId channelId;
}
