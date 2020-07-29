package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import model.chat.MsgType;
import model.chat.RpcMsg;
import utils.ChatMsgUtil;
import utils.MsgProcessor;

public class IdleTimeoutHandler extends SimpleChannelInboundHandler<RpcMsg.Msg> {

    private MsgProcessor msgProcessor = new MsgProcessor();


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent)evt;
        switch (event.state()){
            case READER_IDLE:
                System.out.println("读超时");
                msgProcessor.chennelReadTimeoutEvent(ctx.pipeline().channel());
                break;
            case WRITER_IDLE:
                System.out.println("写超时");
                break;
            case ALL_IDLE:
                System.out.println("读写超时");
                break;
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMsg.Msg msg) throws Exception {
        if (msg.getMsgType() != MsgType.MSGTYPE_PING) {
            ctx.fireChannelRead(msg);
            return;
        }
        if (msg.getMsgType() == MsgType.MSGTYPE_PING) {
            // 回复pong消息
            if(msg.getFromUid() == 0){
                System.out.println("ping消息格式错误");
            }
            ctx.pipeline().channel().writeAndFlush(ChatMsgUtil.buildPongMsg(msg.getFromUid()));
        }

    }
}
