package utils;

import constants.PropertiesFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    public static Jedis jedis = null;
    public static void connection(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(0);
        config.setMaxWaitMillis(10000);

        JedisPool pool = new JedisPool(config, PropertiesFile.REDIS_HOST, PropertiesFile.REDIS_PORT);
        jedis = pool.getResource();
    }



    public static void disConnection(){
        if (jedis != null){
            jedis.close();
        }
    }
}
