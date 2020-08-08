package utils;

import com.alibaba.fastjson.JSON;
import constans.RedisKey;
import io.netty.util.internal.StringUtil;
import model.chat.RpcMsg;
import redis.clients.jedis.Jedis;
import session.ServerSession;
import session.ServerSessionMap;

public class SendMsgUtil {

    public static void sendMsg(RpcMsg.Msg msg) {
        Long toUid = msg.getToUid();
        ServerSession session = ServerSessionMap.getSession(toUid);
        // 判断用户是否在本节点内有会话
        if (session != null && session.getChannel() != null) {
            session.getChannel().writeAndFlush(msg);
            System.out.println("uid=" + toUid + "的用户在本节点内，发给uid=" + toUid + "的消息发出去了");
            return;
        }
        // 判断用户是否在其他节点上
        Jedis jedis = null;
        try {
            jedis = RedisFactory.getJedis();
            // 获取会话 格式  ip:port
            String key = RedisKey.sessionStore(toUid);
            String sessionStr = jedis.get(key);
            System.out.println("key="+key+":value="+sessionStr);
            if (StringUtil.isNullOrEmpty(sessionStr)) {
                System.out.println("uid=" + toUid + "的用户没有登录");
                return;
            }
            String[] hostPort = sessionStr.split(":");
            // 发给对应节点的所监听的队列
            String jsonMsg = JSON.toJSONString(ChatMsgUtil.rpcMsg2ChatMsg(msg));
            System.out.println("序列化后的jsonMsg=" + jsonMsg);
            jedis.lpush(NodeUtil.node(hostPort[0], Integer.parseInt(hostPort[1])), jsonMsg);
            System.out.println("消息发向" + hostPort[0] + ":" + hostPort[1] + "节点");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }
}
