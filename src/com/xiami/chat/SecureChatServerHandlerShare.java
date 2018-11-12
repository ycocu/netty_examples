package com.xiami.chat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

//@ChannelHandler.Sharable
public class SecureChatServerHandlerShare extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("SecureChatServerHandlerShare message is " + s);
    }
}
