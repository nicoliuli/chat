package mq;

import constants.PropertiesFile;
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
            List<String> msg = RedisUtil.jedis.brpop(Integer.MAX_VALUE, NodeUtil.node(PropertiesFile.host, PropertiesFile.port), NodeUtil.node(PropertiesFile.host, PropertiesFile.port));
            if (CollectionUtil.isEmpty(msg)) {
                System.out.println("消费的消息为空");
                continue;
            }
            if (msg.size() == 2) {
                String msgString = msg.get(1);
                // 解析消息并分发
                System.out.println(msgString);
            }
        }
    }
}
