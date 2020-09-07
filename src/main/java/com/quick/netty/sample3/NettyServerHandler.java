package com.quick.netty.sample3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * 1、自定义一个handler，需要继承netty 规定好的某个handlerAdapter
 * 2、这是我们定一个handler 才能成为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读取实际数据

    /**
     *
     * @param ctx 上下文对象，包含管道pipeline ， 通道channel，地址
     * @param msg 客户端发送的数据，默认是object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        System.out.println("server ctx = " + ctx);

        // 将msg装成bytebuffer
        // ByteBuf 是netty提供的，不是NIO的ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        // writeAndFlush 是write + flush
        // 将数据写入到缓存，并刷新
        // 一般来京，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端~~~", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，一般需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
