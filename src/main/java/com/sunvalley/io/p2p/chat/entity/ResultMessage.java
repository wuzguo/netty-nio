package com.sunvalley.io.p2p.chat.entity;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 10:48
 */


public class ResultMessage extends BaseMessage {

    /**
     * 消息内容
     */
    private Object msg;

    ResultMessage(String id, Integer type, Object message) {
        super(id, type, message);
    }
}
