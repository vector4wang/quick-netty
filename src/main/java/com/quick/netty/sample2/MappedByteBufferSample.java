package com.quick.netty.sample2;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 说明：
 * 1、MappedByteBuffer 可以让文件直接在内存（堆外内存）修改
 * 操作系统不需要拷贝一次
 */
public class MappedByteBufferSample {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NIOFileChannel01.txt", "rw");

        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 1、FileChannel.MapMpde.Read_write 使用的读写模式
         * 2、0： 可以直接修改的起始位置
         * 3、5：映射到内存的大小，将文件的多少字节映射到内存
         * 可以直接修改的范围是0-5
         * 实际类型
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');

        randomAccessFile.close();

        System.out.println("修改成功。。。");
    }
}
