package handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import model.chat.ChatMsg;

import java.util.List;

/**
 * 将model转json
 */
public class Msg2JsonEncoder extends MessageToMessageEncoder<ChatMsg> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ChatMsg chatMsg, List<Object> list) throws Exception {
        final String jsonString = JSON.toJSONString(chatMsg);
        list.add(jsonString);
    }
}
