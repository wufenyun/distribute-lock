package com.learning.netty.p1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class NettyServer implements Server {

    private ServerBootstrap server;

    public NettyServer(int inetPort) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new NettyHandler());
                    }
                });
        ChannelFuture future;
        try {
            future = server.bind(inetPort).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(Object message) {
        // TODO Auto-generated method stub

    }

    @Override
    public Object receive() {
        // TODO Auto-generated method stub
        return null;
    }

    public static class NettyHandler extends ChannelHandlerAdapter {
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            /*
             * ByteBuf buf = (ByteBuf) msg; byte[] dst = new
             * byte[buf.readableBytes()]; buf.readBytes(dst);
             * System.out.println("接收到客户端消息：" + msg);
             */
//            MyMessage res = MyMessage.newBuilder().setText("Did you say '" + req.getText() + "'?").build();
//            ch.write(res);
        }
    }
}
