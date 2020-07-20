package listener;

import properties.CommonPropertiesFile;
import redis.clients.jedis.Jedis;
import utils.CollectionUtil;
import utils.NodeUtil;
import utils.RedisUtil;

import java.util.List;

public class ChatMsgConsumer {
    /**
     * 监听当前节点的队列
     */
    public static void start() {
        new Thread(() -> {
            consumer();
        }).start();
    }


    private static void consumer() {
        while (true) {
            Jedis jedis = null;
            try {
                jedis = RedisUtil.getJedis();
                List<String> msg = jedis.brpop(Integer.MAX_VALUE, NodeUtil.node(CommonPropertiesFile.host, CommonPropertiesFile.port), NodeUtil.node(CommonPropertiesFile.host, CommonPropertiesFile.port));
                if (CollectionUtil.isEmpty(msg)) {
                    System.out.println("消费的消息为空");
                    continue;
                }
                if (msg.size() == 2) {
                    String msgString = msg.get(1);
                    // 解析消息并分发
                    System.out.println("来自其他集群的消息："+msgString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                jedis.close();
            }

        }
    }
}
