package model.chat;

/**
 * 消息类型
 */
public class MsgType {
    /**
     * 聊天消息
     */
    public static Integer MSGTYPE_CHAT = 1;

    /**
     * ack消息
     */
    public static Integer MSGTYPE_ACK = 2;

    /**
     * 登录消息
     */
    public static Integer MSGTYPE_LOGIN = 3;

    /**
     * 客户端给服务端发的ping消息
     */
    public static  Integer MSGTYPE_PING = 4;

    /**
     * 客户端给服务端发的pong消息
     */
    public static  Integer MSGTYPE_PONG = 5;
}
