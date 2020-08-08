import dao.InitDao;
import listener.ChatMsgConsumer;
import properties.PropertiesMap;
import server.NettyServer;
import utils.RedisFactory;
import utils.ZkUtil;

public class ChatServerApplication {

    private static ZkUtil zkUtil = null;

    public static void main(String[] args) throws Exception {
        init();
        startup();
    }

    private static void init() {
        try {
            zkUtil = new ZkUtil();
            // 加载配置文件
            PropertiesMap.loadProperties();
            // 连接zk
            zkUtil.connection();
            // 连接redis
            RedisFactory.connection();
            // 开启队列监听
            ChatMsgConsumer consumer = new ChatMsgConsumer();
            consumer.start();
            // 初始化数据
            InitDao.init();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void startup() {
        try {
            // 添加钩子
            shutdownHook();
            new NettyServer(zkUtil).bind(Integer.parseInt(PropertiesMap.getProperties("port")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

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
        zkUtil.delRegistryInfo();
        // 关闭redis连接
        RedisFactory.disConnection();
        // 关闭zk连接
        zkUtil.disConnection();
    }
}
