package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.chat.RpcMsg;
import utils.MsgProcessor;

public class ServerBisHandler extends SimpleChannelInboundHandler<RpcMsg.Msg> {


    private MsgProcessor msgProcessor = new MsgProcessor();

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        // 删除本地会话和集群会话
        msgProcessor.channelInactive(ctx.channel());
        ctx.fireChannelInactive();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        msgProcessor.channelActive(ctx.channel());
        ctx.fireChannelActive();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMsg.Msg msg) throws Exception {
        msgProcessor.msgProcessor(ctx.channel(),msg);
    }


}
