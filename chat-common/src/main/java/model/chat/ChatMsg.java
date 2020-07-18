package model.chat;

import java.util.HashMap;
import java.util.Map;

public class ChatMsg {
    /**
     * 唯一id
     */
    private String msgId;
    /**
     * 消息发送方uid
     */
    private Long fromUid;
    /**
     * 消息接收方uid
     */
    private Long toUid;
    /**
     * 消息格式
     */
    private Integer format;
    /**
     * 消息类型
     */
    private Integer msgType;
    /**
     * 聊天类型
     */
    private Integer chatType;
    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 消息内容
     */
    private Map<String, Object> body = new HashMap();

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Long getFromUid() {
        return fromUid;
    }

    public void setFromUid(Long fromUid) {
        this.fromUid = fromUid;
    }

    public Long getToUid() {
        return toUid;
    }

    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }

    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
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
                '}';
    }
}
