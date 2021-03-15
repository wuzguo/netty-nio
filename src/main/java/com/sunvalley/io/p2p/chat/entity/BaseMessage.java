package com.sunvalley.io.p2p.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 10:50
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessage {

    /**
     * 消息ID
     */
    private String id;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息体
     */
    private Object message;
}
