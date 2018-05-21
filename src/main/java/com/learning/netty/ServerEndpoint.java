package com.learning.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerEndpoint {
    
    public static void main(String[] args) {
        ServerEndpoint server = new ServerEndpoint();
        server.start();
    }
    
    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(10);
        
        bootstrap.group(boss, worker)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ServerHandlerInitializer());
        
        ChannelFuture channelFuture = bootstrap.bind(8080);
        System.out.println("启动完毕");
        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("启动完毕");
    }
    
    
    
    public static class ServerHandlerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast("stringEncoder",new StringEncoder());
            pipeline.addLast("stringDecoder",new StringDecoder());
            pipeline.addLast("serverHandler",new ServerHandler());
        }
        
    }
    
    
    public static class ServerHandler extends SimpleChannelInboundHandler {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("RemoteAddress : " + ctx.channel().remoteAddress() + " active !");
            ctx.writeAndFlush("连接成功！");
            super.channelActive(ctx);
        }
        
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("收到client消息：" + msg);
        }
        
    }
}
