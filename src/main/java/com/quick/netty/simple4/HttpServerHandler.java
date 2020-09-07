package com.quick.netty.simple4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 1、SimpleChannelInboundHandler是ChannelInboundHandlerAdapter
 * 2、httpobject客户端和服务端相互通讯的数据被封装成httpObject
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // 读取客户端
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {

            System.out.println("pipeline hashcode: " +ctx.pipeline().hashCode()+"HttpServerHandler hashcode: "+ this.hashCode());

            System.out.println("msg: " + msg.getClass());
            System.out.println("客户端地址: " + ctx.channel().remoteAddress());

            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("特殊资源，不做响应");
                return;
            }

            // 回复信息给浏览器 【http协议】
            ByteBuf content = Unpooled.copiedBuffer("hello，我是服务器", CharsetUtil.UTF_8);

            //构造一个http的响应，即HTTPResponse
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 将构建好的response返回
            ctx.writeAndFlush(fullHttpResponse);
        }
    }
}
