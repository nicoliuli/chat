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
        try {
            // 添加钩子
            shutdownHook();
            // 加载配置文件
            PropertiesMap.loadProperties();
            // 连接zk
            ZkUtil.connection();
            // 连接redis
            RedisUtil.connection();
            // 开启队列监听
            ChatMsgConsumer consumer = new ChatMsgConsumer();
            consumer.start();
            // 初始化数据
            InitDao.init();
            new NettyServer().bind(Integer.parseInt(PropertiesMap.getProperties("port")));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }


    private static void shutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("优雅停机start");
            shutdown();
            System.out.println("优雅停机end");

        }));
    }

    private static void shutdown() {
        // 删除注册在zk的节点
        ZkUtil.delRegistryInfo();
        // 关闭redis连接
        RedisUtil.disConnection();
        // 关闭zk连接
        ZkUtil.disConnection();
    }
}
