package handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import model.chat.ChatMsg;

/**
 * 将JSON转化为POJO
 */
public class Json2MsgDecoder extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String) {
            String jsonMsg = (String) msg;

            ChatMsg chatMsg = JSON.parseObject(jsonMsg, ChatMsg.class);
            ctx.fireChannelRead(chatMsg);

        }
    }
}
