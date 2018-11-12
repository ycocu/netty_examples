/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.xiami.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.tools.ant.taskdefs.Sleep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends SimpleChannelInboundHandler<String> {
    SocketAddress remoteAddress, localAddress;
    Bootstrap b;

    public void setB(Bootstrap b) {
        this.b = b;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println(msg);
        remoteAddress = ctx.channel().remoteAddress();
        localAddress = ctx.channel().localAddress();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channelActive");

    }
/*
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("++++++++++++Reconnecting server " + SecureChatClient.HOST);
        ctx.channel().eventLoop().schedule(new Runnable() {
            public void run() {
                try {
                    SecureChatClient.ch = SecureChatClient.connect().channel();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }, SecureChatClient.RECONNECT_DELAY, TimeUnit.SECONDS);    }
*/
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Reconnecting server " + SecureChatClient.HOST);
        ctx.channel().eventLoop().schedule(new Runnable() {
            public void run() {
                try {
                    SecureChatClient.ch = SecureChatClient.connect().channel();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }, SecureChatClient.RECONNECT_DELAY, TimeUnit.SECONDS);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        System.out.println("channelReadComplete");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
