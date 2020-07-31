package com.quick.netty.sample2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("NIOFileChannel01.txt");

        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("NIOFileChannel03.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {

            // 清空buffer
            byteBuffer.clear();

            int read = fileChannel01.read(byteBuffer);
            if (read == -1) {
                break;
            }

            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }
    }
}
