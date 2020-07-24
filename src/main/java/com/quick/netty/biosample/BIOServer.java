package com.quick.netty.biosample;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务启动");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        while (true) {
            System.out.println("线程信息： id= " + Thread.currentThread().getId() + " 名字：" + Thread.currentThread().getName() + " 等待连接");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    // handler 方法与客户端通讯
    public static void handler(Socket socket) {

        // 通过scoket获取输入流
        try {
            String theadName = "线程信息： id= " + Thread.currentThread().getId() + " 名字：" + Thread.currentThread().getName();
            System.out.println(theadName);
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();

            // 循环读取了客户端发送的数据
            while (true) {
                System.out.println("read...");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    String prefix = "打印线程信息： id= " + Thread.currentThread().getId() + " 名字：" + Thread.currentThread().getName();
                    System.out.println(prefix + ": " + new String(bytes, 0, read)); // 输出客户端发送的数据
                } else {
                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            String theadName = "线程信息： id= " + Thread.currentThread().getId() + " 名字：" + Thread.currentThread().getName();
            System.out.println("关闭["+theadName+"]和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
