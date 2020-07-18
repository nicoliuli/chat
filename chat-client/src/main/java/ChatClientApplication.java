import client.NettyClient;
import utils.ZkUtil;

public class ChatClientApplication {
    public static void main(String[] args) {
        startup();
    }

    private static void startup(){
        try {
            // 连接zk
            ZkUtil.connection();
            // 获取NettyServer连接信息
            String[] server = ZkUtil.getRandomServer();
            new NettyClient().connect(server[0],Integer.parseInt(server[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutduwn(){
        ZkUtil.disConnection();
    }
}
