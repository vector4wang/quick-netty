package com.quick.netty.sample2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("01.png");
        FileOutputStream fileOutputStream = new FileOutputStream("02.png");

        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        fileOutputStreamChannel.transferFrom(inputStreamChannel, 0, inputStreamChannel.size());


        inputStreamChannel.close();
        fileOutputStreamChannel.close();

        fileInputStream.close();
        fileOutputStream.close();
    }
}
