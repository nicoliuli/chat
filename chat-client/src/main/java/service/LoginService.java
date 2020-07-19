package service;

import constans.RedisKey;
import io.netty.util.internal.StringUtil;
import redis.clients.jedis.Jedis;
import utils.RedisUtil;

import java.util.Scanner;

public class LoginService {
    public static Long login() {
        // 登录
        Long loginUid = -1L;
        do {
            loginUid = doLogin();
            if (loginUid > 0L) {
                break;
            }
            System.out.println("该用户不存在，请重新登录！");
        } while (true);
        return loginUid;
    }

    /**
     *  返回登录的uid
     * @return
     */
    private static Long doLogin() {
        System.out.print("请输入用户id：");
        Scanner sc = new Scanner(System.in);
        String id = null;
        if (sc.hasNextLine()) {
            id = sc.next();
        }
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            String user = jedis.hget(RedisKey.userMapKey(), id);
            if (StringUtil.isNullOrEmpty(user)) {
                return -1L;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return Long.parseLong(id);
    }
}
