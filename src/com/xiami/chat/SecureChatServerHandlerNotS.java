package com.xiami.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class SecureChatServerHandlerNotS extends SimpleChannelInboundHandler<String> {

    private int counter = 0;
    //@Override
    public void channelRead(ChannelHandlerContext ctx, String msg) throws Exception {
        super.channelRead(ctx, msg);
        System.out.println("Channel Read is " + msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("message is " + s + "counter" + ++counter);
        channelHandlerContext.writeAndFlush("[Self] " + s + "\r\n");
    }
}
