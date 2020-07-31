package com.quick.netty.sample2;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        // 举例说明buffer的使用
        IntBuffer intBuffer = IntBuffer.allocate(5);
//        intBuffer.put(10);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        /**
         * 读写切换
         */
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
