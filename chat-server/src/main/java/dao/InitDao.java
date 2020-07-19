package dao;

import com.alibaba.fastjson.JSON;
import constans.RedisKey;
import model.domain.User;
import redis.clients.jedis.Jedis;
import utils.RedisUtil;

import java.util.HashMap;
import java.util.Map;

public class InitDao {
    /**
     * 模拟数据库，创建用户
     */
    private static void createUser() {

        Map<String, String> userMap = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            User u = new User(i + 1L, "用户" + (i + 1));
            userMap.put(u.getUid() + "", JSON.toJSONString(u));
        }
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            if(!jedis.exists(RedisKey.userMapKey())){
                jedis.hset(RedisKey.userMapKey(), userMap);
                jedis.expire(RedisKey.userMapKey(),3600);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void init(){
        createUser();
    }
}
