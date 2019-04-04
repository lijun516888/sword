package com.sword.app.ws;

import com.google.common.collect.Maps;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.net.InetSocketAddress;
import java.util.Map;

public class NettyWsServer {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;

    public static Map<String, Channel> cache = Maps.newConcurrentMap();

    public ChannelFuture startWsServer(InetSocketAddress address) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(this.bossGroup, this.workGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new NettyWsChannelIinitialize());
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = bootstrap.bind(address).syncUninterruptibly();
        channel = future.channel();
        return future;
    }

    public void destroy() {
        if(channel != null) {
            channel.close();
            // channel.closeFuture().syncUninterruptibly();
        }
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        NettyWsServer server = new NettyWsServer();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9090);
        ChannelFuture future = server.startWsServer(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.destroy()));
        //future.channel().close().syncUninterruptibly();
        new Thread(() -> {
            while(true) {
                System.out.println(cache.size());
                if(cache.size() > 0) {
                    for(Map.Entry<String, Channel> entry : cache.entrySet()) {
                        entry.getValue().writeAndFlush(new TextWebSocketFrame("32你好！"));
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
