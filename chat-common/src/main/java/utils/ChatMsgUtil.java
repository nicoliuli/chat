package utils;

import model.chat.ChatMsg;
import model.chat.MsgType;

import java.util.UUID;

/**
 * 构建消息体工具类
 */
public class ChatMsgUtil {
    /**
     * 构建登录消息
     *
     * @param uid
     * @return
     */
    public static ChatMsg builLoginMsg(Long uid) {
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setMsgId(UUID.randomUUID().toString());
        chatMsg.setTimestamp(System.currentTimeMillis());
        chatMsg.setMsgType(MsgType.MSGTYPE_LOGIN);
        chatMsg.setFromUid(uid);
        return chatMsg;
    }
}
