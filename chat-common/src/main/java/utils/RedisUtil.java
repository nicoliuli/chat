package utils;

import constans.RedisKey;
import properties.CommonPropertiesFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static Jedis jedis = null;
    private static JedisPool pool = null;

    public static void connection() throws Exception {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(0);
        config.setMaxWaitMillis(10000);

        pool = new JedisPool(config, CommonPropertiesFile.REDIS_HOST, CommonPropertiesFile.REDIS_PORT);
        jedis = pool.getResource();
    }

    /**
     * 获取jedis
     *
     * @return
     * @throws Exception
     */
    public static Jedis getJedis() throws Exception {
        if (pool == null) {
            throw new Exception("get Jedis fail");
        }
        return pool.getResource();
    }

    public static void disConnection() {
        if (jedis != null) {
            jedis.close();
            jedis.disconnect();
            jedis = null;
        }
        if(pool != null){
            pool.close();
            pool = null;
        }
        System.out.println("close redis ok");
    }


    /**
     * 清除在redis的会话
     * @param host
     * @param port
     * @param uid
     */
    public static void cleanSession(String host,Integer port,Long uid){
        // 这里应该清除，redis里的会话
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.del(RedisKey.sessionStore(uid));
            System.out.println("cleanSession ok");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
