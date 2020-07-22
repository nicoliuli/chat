package client;

import handler.ClientBisHandler;
import handler.ClientPongHandler;
import handler.Json2MsgDecoder;
import handler.Msg2JsonEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import utils.ChannelUtil;

public class NettyClient {
    /**
     * 当前客户端的uid
     */
    private Long uid;

    public NettyClient(Long uid) {
        this.uid = uid;
    }

    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChildChannelHandler());

            ChannelFuture f = b.connect(host, port).sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("链接成功");
                    }
                }
            });
            ChannelUtil.sengLoginMsg(f.channel(),this.uid);
            ChannelUtil.startSendPingMsgSchedul(f.channel(),this.uid);

            f.channel().closeFuture().sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("client close");
                }
            });

        } finally {
            group.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel ch) throws Exception {

            //in解码
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast(new Json2MsgDecoder());
            ch.pipeline().addLast(new ClientPongHandler());
            ch.pipeline().addLast(new ClientBisHandler());
            //out编码
            ch.pipeline().addLast(new LengthFieldPrepender(4));
            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast(new Msg2JsonEncoder());
        }
    }


}
