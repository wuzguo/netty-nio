package com.sunvalley.io.p2p.chat.entity;

import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 10:16
 */

public class AuthMessage extends BaseMessage {

    private Object msg;

    AuthMessage(String id, Integer type, Object message) {
        super(id, type, message);
    }
}
