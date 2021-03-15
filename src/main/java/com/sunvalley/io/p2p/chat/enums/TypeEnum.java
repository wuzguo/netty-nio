package com.sunvalley.io.p2p.chat.enums;

import java.util.Objects;
import lombok.Getter;


@Getter
public enum TypeEnum {
    /**
     * 返回的消息
     */
    RESULT(0, "返回"),

    /**
     * 认证
     */
    AUTH(1, "认证"),

    /**
     * 业务
     */
    BUSINESS(2, "业务"),
    ;

    private final Integer value;

    private final String desc;

    TypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取升级类型
     *
     * @param value 类型
     * @return {@link TypeEnum}
     */
    public static TypeEnum valueOf(Integer value) {
        for (TypeEnum typeEnum : TypeEnum.values()) {
            if (Objects.equals(typeEnum.getValue(), value)) {
                return typeEnum;
            }
        }
        return TypeEnum.AUTH;
    }
}
