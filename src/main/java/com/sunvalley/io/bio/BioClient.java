package com.sunvalley.io.bio;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 6666);
        Scanner scanner = new Scanner(System.in);

        // 判断是否还有输入
        if (scanner.hasNext()) {
            String message = scanner.next();
            System.out.println("输入的数据为：" + message);
            socket.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
        }
        scanner.close();
        socket.close();
    }
}
