package com.sunvalley.io.p2p.chat.entity;

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
 * @date 2021/3/16 14:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultMessage implements Serializable {

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
    private ChannelId to;

    /**
     * 消息体
     */
    private Object message;
}
