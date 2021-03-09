package com.sunvalley.io.p2p.chat.utils;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.experimental.UtilityClass;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/9 15:52
 */

@UtilityClass
public class IdUtils {

    // 生成器
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1000);

    /**
     * 生成用户ID
     * @return 用户ID
     */
    public static Integer idGen() {
        return ID_GENERATOR.incrementAndGet();
    }
}
