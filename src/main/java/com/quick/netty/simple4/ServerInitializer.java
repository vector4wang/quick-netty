package com.quick.netty.simple4;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 加入一个netty提供的httpservercode
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());

        // 增加自定义的handler
        pipeline.addLast("MyHttpServerHandler", new HttpServerHandler());

    }
}
