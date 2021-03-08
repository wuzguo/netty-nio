package com.sunvalley.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress("127.0.0.1", 8888));
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();

                if (selectionKey.isAcceptable()) {
                    SocketChannel accept = socketChannel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if (selectionKey.isReadable()) {
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    channel.read(buffer);
                    System.out.println("get message from client:  " + new String(buffer.array()));
                }
                keyIterator.remove();
            }
        }
    }
}
