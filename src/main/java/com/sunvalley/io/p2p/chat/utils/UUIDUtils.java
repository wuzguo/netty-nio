package com.sunvalley.io.p2p.chat.utils;

import java.util.UUID;
import lombok.experimental.UtilityClass;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 10:53
 */

@UtilityClass
public class UUIDUtils {

    public static String getId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
