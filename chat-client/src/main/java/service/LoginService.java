package service;

import io.netty.util.internal.StringUtil;
import redis.clients.jedis.Jedis;
import utils.RedisUtil;

import java.util.Scanner;

public class LoginService {
    public static boolean login(){
        System.out.print("请输入用户id：");
        Scanner sc = new Scanner(System.in);
        String id = null;
        if(sc.hasNextLine()){
             id = sc.next();
        }
        Jedis jedis = null;
        try{
            jedis = RedisUtil.getJedis();
            String user = jedis.hget("chat:user:map", id);
            if(StringUtil.isNullOrEmpty(user)){
                return false;
            }
        }catch (Exception e){

        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return true;
    }
}
