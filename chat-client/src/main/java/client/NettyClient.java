package client;

import handler.ClientBisHandler;
import handler.ClientPongHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import model.chat.RpcMsg;
import utils.ChannelUtil;
import utils.ScannerUtil;
import utils.ZkUtil;

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
            ChannelUtil.sengLoginMsg(f.channel(), this.uid);
            ChannelUtil.startSendPingMsgSchedule(f.channel(), this.uid);
            ScannerUtil.scanner(f.channel(), this.uid);

            f.channel().closeFuture().sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    // server宕机，或client关闭，会执行这个方法，清除redis会话
                    ChannelUtil.clearSessionStore(uid);
                    System.out.println("client close");
                }
            });

        } finally {
            group.shutdownGracefully();
        }
        System.exit(-1);
    }

    private void reConnect(){
        try{
            String[] server = ZkUtil.getRandomServer();
            connect(server[0], Integer.parseInt(server[1]));
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel ch) throws Exception {

            //in解码
            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
            ch.pipeline().addLast(new ProtobufDecoder(RpcMsg.Msg.getDefaultInstance()));
            ch.pipeline().addLast(new ClientPongHandler());
            ch.pipeline().addLast(new ClientBisHandler());
            //out编码
            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
            ch.pipeline().addLast(new ProtobufEncoder());
            // b1
            // b12
            // b13
            // b14

            // 2
        }
    }


}
