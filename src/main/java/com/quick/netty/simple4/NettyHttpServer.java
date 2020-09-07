package com.quick.netty.simple4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyHttpServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建BossGroup 和 WorkGroup
        // 1、创建两个线程组 bossgroup 和workgroup
        // 2、bossGroup 只是处理连接请求，真正和客户端业务处理交给workerGroup处理
        // 3、两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {


            // 常见服务器端的启动参数，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用NioSocketChannel 作为服务器的通道实现
                    .childHandler(new ServerInitializer());// 给workergroup的eventloop对应的管道设置处理器


            System.out.println("服务器 is ready....");

            // 绑定一个端口并且同步，生成了一个channelFuture 对象
            // 启动服务器
            ChannelFuture sync = bootstrap.bind(8888).sync();

            // 对关闭通道进行监听
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
