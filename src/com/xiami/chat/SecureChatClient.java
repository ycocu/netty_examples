package com.xiami.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.example.telnet.TelnetClient;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Simple SSL chat client modified from {@link TelnetClient}.
 */
public final class SecureChatClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));
    static Bootstrap b = new Bootstrap();
    static final int RECONNECT_DELAY = 5;
    static Channel ch = null;

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        EventLoopGroup group = new NioEventLoopGroup();
        try {

            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SecureChatClientInitializer(sslCtx, b));

            // Start the connection attempt.
            ChannelFuture cf = connect();
            ch = cf.sync().channel();

            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }

                // Sends the received line to the server.
                //lastWriteFuture = ch.writeAndFlush(line + "\r\n");

                for (int i = 0; i < 10; i++) {
                    ByteBuf mmessage = null;
                    String s = "this is a client message i=" + i + "$";
                    lastWriteFuture = ch.writeAndFlush(s);
                    /*mmessage = Unpooled.buffer(s.length());
                    mmessage.writeBytes(s.getBytes());
                    lastWriteFuture = ch.writeAndFlush(mmessage);*/
                    //lastWriteFuture = ch.writeAndFlush(s + "\r\n");
                    //lastWriteFuture = ch.write(s);
                }
                // If user typed the 'bye' command, wait until the server closes
                // the connection.
                if ("bye".equals(line.toLowerCase())) {
                    ch.closeFuture().sync();
                    break;
                }
            }

            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
            System.out.println("Exit main loop...................");
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }
    static public ChannelFuture connect() {
        ChannelFuture cf = b.connect(HOST, PORT);
        cf.addListener( new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.cause() != null) {
                    System.out.println("Failed to connect: " + HOST);
                } else {
                    System.out.println("Connected to " + HOST);
                }
            }
        });
        return cf;
    }
}
