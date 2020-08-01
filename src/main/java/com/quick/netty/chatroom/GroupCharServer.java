package com.quick.netty.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupCharServer {

    // 定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;


    public static void main(String[] args) {
        GroupCharServer groupCharServer = new GroupCharServer();
        groupCharServer.listen();
    }

    public GroupCharServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select(2000);
                if (count > 0) {
                    // 便利得到selectionkey
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        // 监听accept
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + " 上线");
                        }
                        if (key.isReadable()) { // 通道发生read事件 通道可读
                            // 处理读
                            readData(key);
                        }

                        // 当前的key删除，防止重复操作
                        iterator.remove();


                    }
                }else{
//                    System.out.println("等待");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读取客户端消息
    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            // 渠道关联的channel
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            // 根据count做处理
            if (count > 0) {
                String msg = new String(buffer.array());
                // 输出
                System.out.println("from 客户端: " + msg);
                // 向其他客户端发送消息
                sendInfoToOther(msg.trim(), channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了...");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    // 广播
    private void sendInfoToOther(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器准发消息中...");
        for (SelectionKey key : selector.keys()) {
            // 通过key去除对应的socketchannel
            Channel targetChannel = key.channel();
            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }

    }
}
