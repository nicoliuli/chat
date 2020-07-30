import client.NettyClient;
import properties.PropertiesMap;
import service.LoginService;
import utils.RedisUtil;
import utils.ZkUtil;

public class ChatClientApplication {
    private static String[] server = new String[2];
    private static Long uid = 0L;
    public static void main(String[] args) {
        startup();
    }

    private static void startup() {
        try {
            // 优雅停机钩子
            shutdownHook();
            PropertiesMap.loadProperties();
            // 连接zk
            ZkUtil.connection();
            RedisUtil.connection();

            // 获取NettyServer连接信息
            server = ZkUtil.getRandomServer();
            uid = LoginService.login();
            new NettyClient(uid).connect(server[0], Integer.parseInt(server[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutduwn() {
        RedisUtil.cleanSession(server[0], Integer.parseInt(server[1]),uid);
        RedisUtil.disConnection();
        ZkUtil.disConnection();

    }

    private static void shutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            shutduwn();
        }));
    }
}
