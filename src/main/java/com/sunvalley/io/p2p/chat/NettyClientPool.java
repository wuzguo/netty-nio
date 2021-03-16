package com.sunvalley.io.p2p.chat;

import com.google.common.collect.Lists;
import com.sunvalley.io.netty.ExceptionHandler;
import com.sunvalley.io.p2p.chat.codec.PacketDecoder;
import com.sunvalley.io.p2p.chat.codec.PacketEncoder;
import com.sunvalley.io.p2p.chat.entity.BaseMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.FutureListener;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * <B>说明：</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2021/3/15 15:09
 */

public class NettyClientPool {

    /**
     * key为目标host，value为目标host的连接池
     */
    private ChannelPoolMap<String, FixedChannelPool> mapChannelPools;

    /**
     * 主机名称
     */
    private final String hostName;

    /**
     * 端口
     */
    private final Integer port;


    private NettyClientPool(String hostName, Integer port) {
        this.hostName = hostName;
        this.port = port;
    }

    /**
     * 构造函数
     *
     * @param hostName    主机名称
     * @param port        端口
     * @param collections Handler
     */
    public NettyClientPool(String hostName, Integer port, Collection<ChannelHandler> collections) {
        this(hostName, port);
        create(collections);
    }

    private void create(Collection<ChannelHandler> collections) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class)
            .remoteAddress(this.hostName, this.port);
        List<ChannelHandler> channelHandlers = Lists.newArrayList();
        // 各种Handler
        channelHandlers.add(new PacketDecoder());
        channelHandlers.add(new PacketEncoder());
        channelHandlers.add(new ExceptionHandler());
        channelHandlers.addAll(Optional.ofNullable(collections).orElse(Collections.emptyList()));
        create0(bootstrap, channelHandlers);
    }

    /**
     * netty连接池使用
     *
     * @param bootstrap {@link Bootstrap}
     */
    private void create0(@NonNull Bootstrap bootstrap, Collection<ChannelHandler> channelHandlers) {
        mapChannelPools = new AbstractChannelPoolMap<String, FixedChannelPool>() {
            @Override
            protected FixedChannelPool newPool(String key) {
                ChannelPoolHandler handler = new ChannelPoolHandler() {
                    /**
                     * 使用完channel需要释放才能放入连接池
                     *
                     */
                    @Override
                    public void channelReleased(Channel ch) throws Exception {
                        // 刷新管道里的数据
                        System.out.println("channel released ......");
                    }

                    /**
                     * 当链接创建的时候添加channelhandler，只有当channel不足时会创建，但不会超过限制的最大channel数
                     *
                     */
                    @Override
                    public void channelCreated(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        Optional.ofNullable(channelHandlers).orElse(Collections.emptyList()).forEach(pipeline::addLast);
                    }

                    /**
                     *  获取连接池中的channel
                     *
                     */
                    @Override
                    public void channelAcquired(Channel ch) throws Exception {
                        System.out.println("channel acquired ......");
                    }
                };

                // 单个host连接池大小
                return new FixedChannelPool(bootstrap, handler, 50);
            }
        };

    }

    /**
     * 发送请求
     *
     * @param key     Pool Key
     * @param message 消息
     */
    private void send(@NonNull String key, @NonNull Object message) {
        // 从连接池中获取连接
        FixedChannelPool pool = mapChannelPools.get(key);
        // 申请连接，没有申请到或者网络断开，返回null
        pool.acquire().addListener((FutureListener<Channel>) future -> {
            //给服务端发送数据
            Channel channel = future.getNow();
            channel.writeAndFlush(message);
            // 连接放回连接池，这里一定记得放回去
            pool.release(channel);
        });
    }

    /**
     * 发送消息
     *
     * @param message {@link BaseMessage}
     */
    public void sendMessage(@NonNull BaseMessage message) {
        this.send(String.format("%s:%s", this.hostName, this.port), message);
    }
}
