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
     * 接收接收用户ID
     */
    private Integer to;

    /**
     * 发送用户ID
     */
    private Integer from;

    /**
     * 消息体
     */
    private Object msg;
}
