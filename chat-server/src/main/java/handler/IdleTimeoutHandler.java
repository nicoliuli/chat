package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import model.chat.ChatMsg;
import model.chat.MsgType;
import utils.ChatMsgUtil;
import utils.MsgProcessor;

public class IdleTimeoutHandler extends SimpleChannelInboundHandler<ChatMsg> {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent)evt;
        switch (event.state()){
            case READER_IDLE:
                System.out.println("读超时");
                MsgProcessor.chennelReadTimeoutEvent(ctx.pipeline().channel());
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
    protected void channelRead0(ChannelHandlerContext ctx, ChatMsg chatMsg) throws Exception {
        if (chatMsg.getMsgType() != MsgType.MSGTYPE_PING) {
            ctx.fireChannelRead(chatMsg);
            return;
        }
        if (chatMsg.getMsgType() == MsgType.MSGTYPE_PING) {
            // 回复pong消息
            if(chatMsg.getFromUid() == null || chatMsg.getFromUid() == 0){
                System.out.println("ping消息格式错误");
            }
            ctx.pipeline().channel().writeAndFlush(ChatMsgUtil.buildPongMsg(chatMsg.getFromUid()));
        }

    }
}
