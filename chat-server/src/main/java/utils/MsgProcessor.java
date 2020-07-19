package utils;

import io.netty.channel.Channel;
import model.chat.ChatMsg;
import model.chat.MsgType;


public class MsgProcessor {

    public static void msgProcessor(Channel channel, ChatMsg chatMsg) {
        validateChatMsg(chatMsg);
        if (chatMsg.getMsgType() == MsgType.MSGTYPE_LOGIN) {
            System.out.println("uid是 "+chatMsg.getFromUid()+" 的用户登录了");
            // 处理会话数据

        }
    }

    private static void validateChatMsg(ChatMsg chatMsg) {
        if (chatMsg == null) {
            throw new IllegalArgumentException("chatMsg is null");
        }
        if (chatMsg.getMsgType() == null || chatMsg.getMsgType() == 0) {
            throw new IllegalArgumentException("msgType error");
        }
    }
}
