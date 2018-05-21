package com.learning.netty.p1;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;

import com.alibaba.fastjson.JSON;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.ReferenceCountUtil;

public class NettyClient implements Client {

    private Bootstrap client;
    private CallConnetction connection;
    private Channel channel;

    public NettyClient() {
        EventLoopGroup group = new NioEventLoopGroup();
        client = new Bootstrap();
        client.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .handler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        /*
                         * ch.pipeline().addLast(new
                         * LineBasedFrameDecoder(1024));
                         * ch.pipeline().addLast(new StringDecoder());
                         */
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
    }

    @Override
    public void connect(CallConnetction connection) {
        ChannelFuture future;
        future = client.connect(new InetSocketAddress(connection.getServerIp(), connection.getServerPort()));
        this.channel = future.channel();
        System.out.println(future.isDone());
    }

    @Override
    public void send(Object request) {
        ChannelFuture fu;
        fu = getChannel().writeAndFlush(Unpooled.copiedBuffer((byte[]) request));
    }

    @Override
    public Object receive() {
        return null;
    }

    /**
     * @return the connection
     */
    public CallConnetction getConnection() {
        return connection;
    }

    /**
     * @param connection
     *            the connection to set
     */
    public void setConnection(CallConnetction connection) {
        this.connection = connection;
    }

    public Bootstrap getClient() {
        return client;
    }

    public void setClient(Bootstrap client) {
        this.client = client;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    class ClientHandler extends ChannelHandlerAdapter {
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            try {
                System.out.println("server" + msg.toString());
            } finally {
                ReferenceCountUtil.release(msg);// 释放缓冲区
            }
        }
    }

}
