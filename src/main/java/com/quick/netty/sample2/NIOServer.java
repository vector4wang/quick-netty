package com.quick.netty.sample2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建serversocketchannel -> serversocket

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 创建selector对象
        Selector selector = Selector.open();

        //绑定端口 服务监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把serverSocketChannel 注册到selector 关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {

            // 等待1s，如果没有事件发生，返回
            if (selector.select(1000) == 0) { // 没有事件发生
                System.out.println("服务器等待1s，无连接");
                continue;
            }

            // 如果返回>0 获取到相关的selectionkey集合
            // 1、如果返回>0 ，标识已经获取到关注的事件
            // 2、selector.selectedKeys() 返回关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                // 获取到selectionkey
                SelectionKey key = keyIterator.next();
                // 根据key对应的通道发生的事件做相应处理
                if (key.isAcceptable()) {// 如果是OP_ACCEPT，有新的客户端连接
                    // 该客户端端生产一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成了一个socketchannel " + socketChannel.hashCode());
                    // 将socketchannel 设置为飞阻塞
                    socketChannel.configureBlocking(false);
                    // 将socketchannel 注册到selector,关注事件为OP_READ，同时给socketchannel
                    // 关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) { // 发生OP_READ
                    // 通过key 凡想获取到对应的channel
                    SocketChannel channel = (SocketChannel)key.channel();
                    // 获取到改channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端 " + new String(buffer.array()));

                }

                // 手动从集合中移动当前的selecttionkey,防止重复操作
                keyIterator.remove();
            }

        }

    }
}
