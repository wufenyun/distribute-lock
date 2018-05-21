package com.learning.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientEndpoint {
    
    public static void main(String[] args) {
        ClientEndpoint client = new ClientEndpoint();
        client.start();
    }
    
    public void start() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(worker)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, true)
        .handler(new ClientChannelInitializer());
        try {
            Channel channel = bootstrap.connect("localhost", 8080).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                String msg = reader.readLine();
                if(msg == null) {
                    continue;
                } else {
                    channel.write(msg);
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("连接成功");
    }

    public static class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            //字符串解码和编码
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());
            //客户端的逻辑
            pipeline.addLast("handler", new ClientHandler());
        }
    }
    
    public static class ClientHandler extends SimpleChannelInboundHandler{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("receive message:" + msg);
        }
        
    }
}
