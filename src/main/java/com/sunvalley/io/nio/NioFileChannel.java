package com.sunvalley.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/4 10:13
 */

public class NioFileChannel {


    public static void main(String[] args) throws IOException {
        File file = new File("D:\\docs\\drools\\drools.txt");
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel = inputStream.getChannel();
        FileOutputStream outputStream = new FileOutputStream("D:\\docs\\drools\\drools1.txt");
        FileChannel streamChannel = outputStream.getChannel();
        streamChannel.transferFrom(channel, 0, channel.size());
        streamChannel.close();
        inputStream.close();
    }
}
