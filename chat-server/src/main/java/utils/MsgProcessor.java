package utils;

import com.alibaba.fastjson.JSON;
import constans.RedisKey;
import io.netty.channel.Channel;
import model.chat.ChatMsg;
import model.chat.MsgType;
import model.domain.User;
import properties.PropertiesFile;
import redis.clients.jedis.Jedis;
import session.ServerSession;
import session.ServerSessionMap;


public class MsgProcessor {

    public static void msgProcessor(Channel channel, ChatMsg chatMsg) {
        validateChatMsg(chatMsg);
        if (chatMsg.getMsgType() == MsgType.MSGTYPE_LOGIN) {
            System.out.println("uid是 " + chatMsg.getFromUid() + " 的用户登录了");
            Jedis jedis = null;
            try {
                jedis = RedisUtil.getJedis();
                String userJson = jedis.hget(RedisKey.userMapKey(), chatMsg.getFromUid() + "");
                User u = JSON.parseObject(userJson, User.class);

                // 处理本机器内会话
                ServerSession session = new ServerSession(u, channel);
                ServerSessionMap.add(chatMsg.getFromUid(), session);

                //处理集群会话,value->ip:port
                jedis.set(RedisKey.sessionStore(chatMsg.getFromUid()), NodeUtil.node(PropertiesFile.getHost(), PropertiesFile.port));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }


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
