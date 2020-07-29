package handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import model.chat.MsgType;
import model.chat.RpcMsg;

/**
 * 过滤服务端的pong消息
 */
public class ClientPongHandler extends SimpleChannelInboundHandler<RpcMsg.Msg> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMsg.Msg msg) throws Exception {
        if (msg.getMsgType() == MsgType.MSGTYPE_PONG) {
            return;
        }
        ctx.fireChannelRead(msg);
    }
}
