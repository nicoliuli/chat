package handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.chat.ChatMsg;
import model.chat.MsgType;

/**
 * 过滤服务端的pong消息
 */
public class ClientPongHandler extends SimpleChannelInboundHandler<ChatMsg> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMsg chatMsg) throws Exception {
        if (chatMsg.getMsgType() == MsgType.MSGTYPE_PONG) {
            return;
        }
        ctx.fireChannelRead(chatMsg);
    }
}
