package utils;

import com.alibaba.fastjson.JSON;
import constans.RedisKey;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import model.chat.ChatMsg;
import model.chat.ChatType;
import model.chat.MsgType;
import model.domain.User;
import properties.CommonPropertiesFile;
import properties.PropertiesFile;
import redis.clients.jedis.Jedis;
import session.ServerSession;
import session.ServerSessionMap;


public class MsgProcessor {

    public static void msgProcessor(Channel channel, ChatMsg chatMsg) {
        validateChatMsg(chatMsg);
        if (chatMsg.getMsgType() == MsgType.MSGTYPE_LOGIN) {
            System.out.println("登录消息==> uid是 " + chatMsg.getFromUid() + " 的用户登录了");
            Jedis jedis = null;
            try {
                jedis = RedisUtil.getJedis();
                String userJson = jedis.hget(RedisKey.userMapKey(), chatMsg.getFromUid() + "");
                User u = JSON.parseObject(userJson, User.class);

                // 处理本机器内会话
                ServerSession session = new ServerSession(u, channel).bind();
                ServerSessionMap.add(chatMsg.getFromUid(), session);

                //处理集群会话,value->ip:port
                jedis.set(RedisKey.sessionStore(chatMsg.getFromUid()), NodeUtil.node(CommonPropertiesFile.getHost(), PropertiesFile.port));
                // 暂时模拟向其他用户发消息，打通链路
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        } else if (chatMsg.getMsgType() == MsgType.MSGTYPE_CHAT) { // 聊天消息
        if (chatMsg.getChatType() == ChatType.SINGLE) {
            System.out.println("收到client fromUid= " + chatMsg.getFromUid() + " 的消息");
            SendMsgUtil.sendMsg(chatMsg);
        }
    }
    }

    /**
     * 处理断开连接的事件
     * @param channel
     */
    public static void channelInactive(Channel channel) {
        Attribute<Object> serverSessionKey = channel.attr(AttributeKey.valueOf("SESSION_KEY"));
        Object obj = serverSessionKey.get();
        if (obj instanceof ServerSession) {
            ServerSession session = (ServerSession) obj;
            User user = session.getUser();
            System.out.println("连接断开事件==>" + user + "断开连接");
            ServerSessionMap.removeSession(user.getUid());
            Jedis jedis = null;
            try {
                jedis = RedisUtil.getJedis();
                jedis.del(RedisKey.sessionStore(user.getUid()));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    /**
     * 处理读超时时间
     * @param channel
     */
    public static void chennelReadTimeoutEvent(Channel channel) {
        if (channel == null) {
            return;
        }
        channel.close().addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                System.out.println("客户端长时间没有发送ping消息，读超时，关闭与客户端连接");
            }
        });
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
