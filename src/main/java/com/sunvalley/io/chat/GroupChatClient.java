package com.sunvalley.io.chat;

import io.netty.util.CharsetUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/4 11:37
 */

public class GroupChatClient {

    /**
     * 管道
     */
    private final SocketChannel socketChannel;

    /**
     * 选择器
     */
    private final Selector selector;

    /**
     * 线程池
     */
    private static final ExecutorService cacheThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L,
        TimeUnit.SECONDS, new SynchronousQueue<>(), Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.AbortPolicy());

    private GroupChatClient(SocketChannel socketChannel, Selector selector) {
        this.socketChannel = socketChannel;
        this.selector = selector;
    }

    public static GroupChatClient start() throws IOException {
        return new GroupChatClient(SocketChannel.open(), Selector.open());
    }

    /**
     * 接收消息
     */
    public GroupChatClient receiveMessage() {
        cacheThreadPool.submit(() -> {
            try {
                this.readMessage();
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        return this;
    }

    private GroupChatClient register(String hostName, Integer port) throws IOException {
        this.socketChannel.connect(new InetSocketAddress(hostName, port));
        this.socketChannel.configureBlocking(false);
        this.socketChannel.register(this.selector, SelectionKey.OP_READ);
        return this;
    }

    /**
     * 发送消息
     *
     * @param Message 消息
     * @throws IOException
     */
    private void sendMessage(String Message) throws IOException {
        socketChannel.write(ByteBuffer.wrap(Message.getBytes(CharsetUtil.UTF_8)));
    }

    /**
     * 读取消息
     *
     * @throws IOException
     */
    private void readMessage() throws IOException {
        if (this.selector.select() > 0) {
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer);
                    String message = new String(buffer.array()).trim();
                    System.out.println("收到来自:  " + channel.getRemoteAddress() + " 的消息： " + message);
                }
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient chatClient = GroupChatClient.start().register("127.0.0.1", 6668).receiveMessage();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            chatClient.sendMessage(message.trim());
        }
    }
}
