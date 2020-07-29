package handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.chat.RpcMsg;

public class ClientBisHandler extends SimpleChannelInboundHandler<RpcMsg.Msg> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMsg.Msg msg) throws Exception {
        System.out.println("收到 "+msg.getFromUid()+" 的消息：" + msg.getBody());

    }
}
