package com.quick.netty.sample2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String str = "hello vector4wang";

        // 创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("NIOFileChannel01.txt");

        // 通过fileoutputstream获取channel
        // filechannel真实类型是FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        fileOutputStream.close();

    }
}
