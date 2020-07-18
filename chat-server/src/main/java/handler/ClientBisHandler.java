package handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.ChatMsg;

public class ClientBisHandler extends SimpleChannelInboundHandler {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if(o instanceof ChatMsg){
            ChatMsg chatMsg = (ChatMsg) o;
            System.out.println(chatMsg);
        }
    }
}
