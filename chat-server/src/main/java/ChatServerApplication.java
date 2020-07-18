import mq.ChatMsgConsumer;
import server.NettyServer;
import utils.RedisUtil;
import utils.ZkUtil;

public class ChatServerApplication {

    public static void main(String[] args) throws Exception{
        onInit();

    }

    public static void onInit(){
        ZkUtil.connection();
        RedisUtil.connection();
        ChatMsgConsumer.start();
        new NettyServer().bind(8080);
    }

    public static  void shutdown(){
        ZkUtil.disConnection();
        RedisUtil.disConnection();
    }
}
