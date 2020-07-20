package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.chat.ChatMsg;
import utils.MsgProcessor;

import java.util.concurrent.atomic.AtomicInteger;

public class ServerBisHandler extends SimpleChannelInboundHandler<ChatMsg> {
    public static AtomicInteger connectionCount = new AtomicInteger(0);


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断开连接，当前连接数：" + connectionCount.decrementAndGet());
        // 删除本地会话和集群会话
        MsgProcessor.channelInactive(ctx.channel());

        ctx.fireChannelInactive();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有新连接了，当前连接数：" + connectionCount.incrementAndGet());
        ctx.fireChannelActive();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMsg chatMsg) throws Exception {
        MsgProcessor.msgProcessor(ctx.pipeline().channel(),chatMsg);
    }


}
