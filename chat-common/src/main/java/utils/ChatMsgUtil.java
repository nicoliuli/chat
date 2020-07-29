package utils;

import com.alibaba.fastjson.JSON;
import model.chat.*;

import java.util.*;

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
    public static RpcMsg.Msg builLoginMsg(Long uid) {
        RpcMsg.Msg msg = RpcMsg.Msg.newBuilder()
                .setMsgId(UUID.randomUUID().toString())
                .setTimestamp(System.currentTimeMillis())
                .setMsgType(MsgType.MSGTYPE_LOGIN)
                .setFromUid(uid)
                .build();
        return msg;

    }

    /**
     * 构建私聊天消息
     *
     * @return
     */
    public static RpcMsg.Msg buildSingleChatMsg(Long fromUid, Long toUid,String text) {
        Map<String, Object> body = new HashMap<>();
        body.put("text", text);
        RpcMsg.Msg msg = RpcMsg.Msg.newBuilder()
                .setFromUid(fromUid)
                .setToUid(toUid)
                .setMsgType(MsgType.MSGTYPE_CHAT)
                .setMsgId(UUID.randomUUID().toString())
                .setTimestamp(System.currentTimeMillis())
                .setFormat(MsgFormat.FORMAT_TXT)
                .setChatType(ChatType.SINGLE)
                .setBody(JSON.toJSONString(body))
                .build();

        return msg;
    }

    public static RpcMsg.Msg buildGroupChatMsg(Long fromUid, List<String> toUids, String text){
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setFromUid(fromUid);
        chatMsg.setMsgType(MsgType.MSGTYPE_CHAT);
        chatMsg.setMsgId(UUID.randomUUID().toString());
        chatMsg.setTimestamp(System.currentTimeMillis());
        chatMsg.setFormat(MsgFormat.FORMAT_TXT);
        chatMsg.setChatType(ChatType.GROUP);
        Map<String, Object> body = new HashMap<>();
        body.put("text", text);
        chatMsg.setBody(body);
        if(!CollectionUtil.isEmpty(toUids)){
            List<Long> toUidList = new ArrayList<>();
            for (String toUid : toUids) {
                toUidList.add(Long.parseLong(toUid));
            }
            chatMsg.setToUidList(toUidList);
        }
        return chatMsg2RpcMsg(chatMsg);
    }

    public static RpcMsg.Msg chatMsg2RpcMsg(ChatMsg chatMsg) {
        RpcMsg.Msg msg = RpcMsg.Msg.newBuilder()
                .setMsgId(chatMsg.getMsgId())
                .setMsgType(chatMsg.getMsgType())
                .setChatType(chatMsg.getChatType())
                .setFormat(chatMsg.getFormat())
                .setFromUid(chatMsg.getFromUid())
                .setToUid(chatMsg.getToUid())
                .setTimestamp(chatMsg.getTimestamp())
                .setBody(JSON.toJSONString(chatMsg.getBody()))
                .addAllToUidList(chatMsg.getToUidList()).build();
        return msg;
    }

    public static ChatMsg rpcMsg2ChatMsg(RpcMsg.Msg msg){
        ChatMsg chatMsg= new ChatMsg();
        chatMsg.setMsgId(msg.getMsgId());
        chatMsg.setMsgType(msg.getMsgType());
        chatMsg.setChatType(msg.getChatType());
        chatMsg.setFormat(msg.getFormat());
        chatMsg.setFromUid(msg.getFromUid());
        chatMsg.setToUid(msg.getToUid());
        chatMsg.setTimestamp(msg.getTimestamp());
        chatMsg.setBody(JSON.parseObject(msg.getBody(),Map.class));
        chatMsg.setToUidList(msg.getToUidListList());
        return chatMsg;
    }
    /**
     * 构建私PING消息
     *
     * @return
     */
    public static RpcMsg.Msg buildPingMsg(Long fromUid) {
        RpcMsg.Msg msg = RpcMsg.Msg.newBuilder()
                .setMsgType(MsgType.MSGTYPE_PING)
                .setMsgId(UUID.randomUUID().toString())
                .setTimestamp(System.currentTimeMillis())
                .setFromUid(fromUid).build();
        return msg;
    }

    /**
     * 构建私PING消息
     *
     * @return
     */
    public static RpcMsg.Msg buildPongMsg(Long toUid) {
        RpcMsg.Msg msg = RpcMsg.Msg.newBuilder()
                .setMsgType(MsgType.MSGTYPE_PONG)
                .setToUid(toUid)
                .setTimestamp(System.currentTimeMillis())
                .setMsgId(UUID.randomUUID().toString())
                .build();

        return msg;
    }
}
