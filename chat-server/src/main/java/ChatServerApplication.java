import dao.InitDao;
import listener.ChatMsgConsumer;
import properties.PropertiesFile;
import properties.PropertiesSchdule;
import server.NettyServer;
import utils.RedisUtil;
import utils.ZkUtil;

public class ChatServerApplication {

    public static void main(String[] args) throws Exception {
        startup();
    }

    public static void startup() {
        try{
            PropertiesSchdule.loadProperties();
            ZkUtil.connection();
            RedisUtil.connection();
            // 开启队列监听
            ChatMsgConsumer.start();
            InitDao.init();
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
