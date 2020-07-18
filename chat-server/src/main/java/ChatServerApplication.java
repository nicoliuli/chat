import constants.PropertiesFile;
import listener.ChatMsgConsumer;
import server.NettyServer;
import utils.RedisUtil;
import utils.ZkUtil;

public class ChatServerApplication {

    public static void main(String[] args) throws Exception {
        startup();
    }

    public static void startup() {
        try{
            ZkUtil.connection();
            RedisUtil.connection();
            // 开启队列监听
            ChatMsgConsumer.start();
            new NettyServer().bind(PropertiesFile.port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        ZkUtil.disConnection();
        RedisUtil.disConnection();
    }
}
