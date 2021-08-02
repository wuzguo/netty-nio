package com.sunvalley.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BioServer {

    public static void main(String[] args) throws IOException {
        SocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(address);

        ExecutorService cacheThreadPool = new ThreadPoolExecutor(4, 8, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        while (true) {
            Socket socket = serverSocket.accept();
            cacheThreadPool.submit(() -> {
                try {
                    handler(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    private static void handler(Socket socket) throws IOException {
        System.out.println("当前线程： " + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
        InputStream stream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder builder = new StringBuilder();
        while ((len = stream.read(bytes)) != -1) {
            //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            builder.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
        }
        System.out.println("get message from client:  " + builder);
    }
}
