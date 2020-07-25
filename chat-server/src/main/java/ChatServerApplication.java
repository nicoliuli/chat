import dao.InitDao;
import listener.ChatMsgConsumer;
import properties.PropertiesMap;
import server.NettyServer;
import utils.RedisUtil;
import utils.ZkUtil;

public class ChatServerApplication {

    public static void main(String[] args) throws Exception {
        startup();
    }

    public static void startup() {
        try{
            PropertiesMap.loadProperties();
            ZkUtil.connection();
            RedisUtil.connection();
            // 开启队列监听
            ChatMsgConsumer consumer = new ChatMsgConsumer();
            consumer.start();
            InitDao.init();
            new NettyServer().bind(Integer.parseInt(PropertiesMap.getProperties("port")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        ZkUtil.disConnection();
        RedisUtil.disConnection();
    }
}
