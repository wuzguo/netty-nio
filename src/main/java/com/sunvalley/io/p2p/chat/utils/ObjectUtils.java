package com.sunvalley.io.p2p.chat.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import lombok.experimental.UtilityClass;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/16 14:15
 */

@UtilityClass
public class ObjectUtils {

    /**
     * 对象转字节数组
     */
    public static byte[] objectToBytes(Object obj) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(outputStream)) {
            objectStream.writeObject(obj);
            objectStream.flush();
            byte[] bytes = outputStream.toByteArray();
            return bytes;
        } catch (IOException e) {
            System.out.println(String.format("object to bytes exception: %s", e.getMessage()));
        }
        return null;
    }

    /**
     * 字节数组转对象
     */
    public static Object bytesToObject(byte[] bytes) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectStream = new ObjectInputStream(inputStream)) {
            return objectStream.readObject();
        } catch ( Exception e) {
            System.out.println(String.format("bytes to object exception: %s", e.getMessage()));
        }
        return null;
    }
}
