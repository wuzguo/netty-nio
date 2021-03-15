package com.sunvalley.io.p2p.chat.entity;

import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 16:08
 */

@Data
public class Message {

    /**
     * 1: 认证；2 业务消息
     */
    private Integer type;

    /**
     * 消息体
     */
    private BaseMessage msg;
}
