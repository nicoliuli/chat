package model.chat;

import java.util.*;

public class ChatMsg {
    /**
     * 唯一id
     */
    private String msgId;
    /**
     * 消息发送方uid
     */
    private long fromUid;
    /**
     * 消息接收方uid
     */
    private long toUid;
    /**
     * 消息格式
     */
    private int format;
    /**
     * 消息类型
     */
    private int msgType;
    /**
     * 聊天类型
     */
    private int chatType;
    /**
     * 时间戳
     */
    private long timestamp;
    /**
     * 消息内容
     */
    private Map<String, Object> body = new HashMap();

    /**
     * 消息接收方uid列表，主要是为群聊
     */
    private Set<Long> toUidList = new HashSet<>();

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public long getFromUid() {
        return fromUid;
    }

    public void setFromUid(long fromUid) {
        this.fromUid = fromUid;
    }

    public long getToUid() {
        return toUid;
    }

    public void setToUid(long toUid) {
        this.toUid = toUid;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }


    public Set<Long> getToUidList() {
        return toUidList;
    }

    public void setToUidList(List<Long> toUidList) {
        this.toUidList.addAll(toUidList);
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "msgId='" + msgId + '\'' +
                ", fromUid=" + fromUid +
                ", toUid=" + toUid +
                ", format=" + format +
                ", msgType=" + msgType +
                ", chatType=" + chatType +
                ", timestamp=" + timestamp +
                ", body=" + body +
                ", toUidList=" + toUidList +
                '}';
    }
}
