package mq;

import utils.RedisUtil;

import java.util.List;

public class ChatMsgConsumer {
    public static void start(){
        new Thread(()->{
            consumer();
        }).start();
    }



    private static  void consumer(){
        while (true){
             List<String> zzz = RedisUtil.jedis.brpop(100000,"zzz","aaa");
             for(String z:zzz){
                 System.out.println(z);
             }
        }
    }
}
