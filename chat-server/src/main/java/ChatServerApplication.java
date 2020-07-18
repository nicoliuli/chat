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
        ZkUtil.connection();
        RedisUtil.connection();
        ChatMsgConsumer.start();
        new NettyServer().bind(PropertiesFile.port);
    }

    public static void shutdown() {
        ZkUtil.disConnection();
        RedisUtil.disConnection();
    }
}
