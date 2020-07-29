package server;

import handler.IdleTimeoutHandler;
import handler.ServerBisHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import model.chat.RpcMsg;
import properties.PropertiesMap;
import utils.ZkUtil;

import java.net.Inet4Address;
import java.util.concurrent.TimeUnit;

public class NettyServer {
    public static Channel channel = null;

    public void bind(int port) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            ChannelFuture f = b.bind(port).sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("server启动成功了");
                        ZkUtil.registerNettyServerNode(Inet4Address.getLocalHost().getHostAddress(), Integer.parseInt(PropertiesMap.getProperties("port")));
                    }
                }
            });
            channel = f.channel();
            f.channel().closeFuture().sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("关闭1");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            System.out.println("关闭2");
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel ch) throws Exception {
            //in解码
            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
            ch.pipeline().addLast(new ProtobufDecoder(RpcMsg.Msg.getDefaultInstance()));
            ch.pipeline().addLast(new IdleStateHandler(10, 10, 0, TimeUnit.SECONDS));
            ch.pipeline().addLast(new IdleTimeoutHandler());
            ch.pipeline().addLast("serverBisHandler", new ServerBisHandler());
            //out编码
            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
            ch.pipeline().addLast(new ProtobufEncoder());
        }
    }
}
