package com.sunvalley.io.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/4 11:12
 */

public class GroupChatServer {

    /**
     * 管道
     */
    private final ServerSocketChannel socketChannel;

    /**
     * 选择器
     */
    private final Selector selector;


    private GroupChatServer(ServerSocketChannel socketChannel, Selector selector) {
        this.socketChannel = socketChannel;
        this.selector = selector;
    }

    public static GroupChatServer start() throws IOException {
        return new GroupChatServer(ServerSocketChannel.open(), Selector.open());
    }

    public ServerSocketChannel getSocketChannel() {
        return socketChannel;
    }

    public Selector getSelector() {
        return selector;
    }

    /**
     * 注册
     *
     * @param port 端口
     * @return {@link Selector}
     * @throws IOException
     */
    private GroupChatServer register(Integer port) throws IOException {
        socketChannel.bind(new InetSocketAddress(port));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        return this;
    }

    /**
     * 监听
     *
     * @throws IOException
     */
    private void listen() throws IOException {
        while (true) {
            if (selector.select() == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    SocketChannel accept = socketChannel.accept();
                    System.out.println("收到来自：" + accept.getRemoteAddress() + "的连接");
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (next.isReadable()) {
                    this.readValue(next);
                }
                iterator.remove();
            }
        }
    }

    /**
     * 读取消息
     *
     * @param selectionKey {@link SelectionKey}
     * @throws IOException
     */
    private void readValue(SelectionKey selectionKey) throws IOException {
        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        channel.read(buffer);
        String message = new String(buffer.array()).trim();
        System.out.println("收到来自:  " + channel.getRemoteAddress() + " 的消息： " + message);
        this.broadcast(message, channel, selector);
    }

    /**
     * 广播消息
     *
     * @param message  消息
     * @param channel  管道
     * @param selector 选择器
     * @throws IOException
     */
    private void broadcast(String message, SocketChannel channel, Selector selector) throws IOException {
        for (SelectionKey selectionKey : selector.keys()) {
            SelectableChannel selectable = selectionKey.channel();
            // 如果不是自己就发送
            if (selectable instanceof SocketChannel && selectable != channel) {
                SocketChannel socketChannel = (SocketChannel) selectable;
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                socketChannel.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        try {
            GroupChatServer.start().register(6668).listen();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
