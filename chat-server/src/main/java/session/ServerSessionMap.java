package session;

import constans.RedisKey;
import properties.CommonPropertiesFile;
import properties.PropertiesMap;
import redis.clients.jedis.Jedis;
import utils.RedisUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储session
 */
public class ServerSessionMap {
    private static ConcurrentHashMap<Long,ServerSession> sessionMap = new ConcurrentHashMap();


    public static void add(Long uid,ServerSession session){
        sessionMap.put(uid,session);
    }

    public static void removeSession(Long uid){
        sessionMap.remove(uid);
    }

    public static ServerSession getSession(Long uid){
        return sessionMap.get(uid);
    }

    /**
     * server退出的时候，删除当前节点redis的所有会话
     */
    public static void cleanSessionStoreMap(){
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            jedis.del(RedisKey.getSessionStoreMapKey(CommonPropertiesFile.getHost(), Integer.parseInt(PropertiesMap.getProperties("port"))));
            System.out.println("del node session ok");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
