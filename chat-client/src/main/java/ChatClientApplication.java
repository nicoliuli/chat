import client.NettyClient;
import properties.PropertiesMap;
import service.LoginService;
import utils.RedisUtil;
import utils.ZkUtil;

public class ChatClientApplication {
    public static void main(String[] args) {
        startup();
    }

    private static void startup() {
        try {
            PropertiesMap.loadProperties();
            // 连接zk
            ZkUtil.connection();
            RedisUtil.connection();

            // 获w取NettyServer连接信息
            String[] server = ZkUtil.getRandomServer();
            Long uid = LoginService.login();
            new NettyClient(uid).connect(server[0], Integer.parseInt(server[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutduwn() {
        ZkUtil.disConnection();
    }
}
