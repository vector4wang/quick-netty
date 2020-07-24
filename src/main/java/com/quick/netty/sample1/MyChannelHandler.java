package com.quick.netty.sample1;

import org.jboss.netty.channel.*;

public class MyChannelHandler extends SimpleChannelHandler {

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Channel closed " + e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Channel connected " + e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        try {
            System.out.println("New message " + e.toString() + " from "
                    + ctx.getChannel());
            processMessage(e);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    private void processMessage(MessageEvent e) {
        Channel ch = e.getChannel();
        System.out.println(e.getMessage().toString());
        ch.write(e.getMessage());
    }
}
