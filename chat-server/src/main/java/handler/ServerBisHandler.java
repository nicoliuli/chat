package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.chat.ChatMsg;
import utils.MsgProcessor;

public class ServerBisHandler extends SimpleChannelInboundHandler<ChatMsg> {




    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        // 删除本地会话和集群会话
        MsgProcessor.channelInactive(ctx.channel());

        ctx.fireChannelInactive();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MsgProcessor.channelActive(ctx.channel());
        ctx.fireChannelActive();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMsg chatMsg) throws Exception {
        MsgProcessor.msgProcessor(ctx.pipeline().channel(),chatMsg);
    }


}
