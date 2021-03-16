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
 * @date 2021/3/15 10:06
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessMessage implements Serializable {

    /**
     * 类型 0： 添加集合 1：发消息
     */
    private Integer type;

    /**
     * 发送用户ID
     */
    private String from;

    /**
     * 接收接收用户ID
     */
    private String to;

    /**
     * 消息体
     */
    private Object message;

    /**
     * 通道
     */
    private ChannelId channelId;
}
