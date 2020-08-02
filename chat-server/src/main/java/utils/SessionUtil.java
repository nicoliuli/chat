package utils;

import constans.RedisKey;
import io.netty.channel.Channel;
import redis.clients.jedis.Jedis;
import session.ServerSession;
import session.ServerSessionMap;

public class SessionUtil {
    /**
     * 清空uid的会话
     *
     * @param jedis
     * @param uid
     */
    public static void clearUserSession(Jedis jedis, Long uid) {
        // 删除本地会话
        ServerSession session = ServerSessionMap.getSession(uid);
        if (session != null) {
            Channel ch = session.getChannel();
            if (ch != null) {
                ch.close();
            }
        }
        ServerSessionMap.removeSession(uid);
        //删除集群会话
        jedis.del(RedisKey.sessionStore(uid));
    }
}
