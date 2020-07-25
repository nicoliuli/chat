package handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.chat.ChatMsg;

public class ClientBisHandler extends SimpleChannelInboundHandler<ChatMsg> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMsg chatMsg) throws Exception {
        System.out.println("收到 "+chatMsg.getFromUid()+" 的消息：" + chatMsg);

    }
}
