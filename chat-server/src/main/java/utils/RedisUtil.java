package utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    public static Jedis jedis = null;
    public static void connection(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(2);
        config.setMaxWaitMillis(5000);

        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);
        jedis = pool.getResource();
    }



    public static void disConnection(){
        if (jedis != null){
            jedis.close();
        }
    }
}