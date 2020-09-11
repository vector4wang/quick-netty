package com.quick.netty.fileserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Copyright (C), 2015-2020, 顺丰科技有限公司
 *
 * @author 01390942
 * @Description
 * @create 2020/9/11
 * @since 1.0.0
 */
public class FileServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 5));// 5m
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new FileServerHandler());
    }
}