package com.sunvalley.io.p2p.chat.entity;

import com.sunvalley.io.p2p.chat.utils.IdUtils;
import io.netty.channel.Channel;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 15:48
 */

@Data
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
    private Integer uid;

    /**
     * 地址
     */
    private String addr;

    /**
     * 通道
     */
    private Channel channel;

    /**
     * 构造器
     *
     * @param addr    地址
     * @param channel 通道
     */
    public User(String addr, Channel channel) {
        this.addr = addr;
        this.channel = channel;
        this.uid = IdUtils.idGen();
    }
}
