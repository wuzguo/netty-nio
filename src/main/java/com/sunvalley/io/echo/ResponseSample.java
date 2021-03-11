package com.sunvalley.io.echo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/11 10:04
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSample {

    /**
     * 编码
     */
    private String code;

    /**
     * 数据消息
     */
    private String message;

    /**
     * 时间戳
     */
    private Long timestamp;
}
