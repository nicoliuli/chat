package utils;

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
            jedis.disconnect();
        }
    }

}
