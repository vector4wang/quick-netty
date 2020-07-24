package com.quick.netty.sample1;

public class NettyServer {
    final static int port = 8080;

    public static void main(String[] args) {
        Server server = new Server();
        server.config(port);
        server.start();
    }
}


