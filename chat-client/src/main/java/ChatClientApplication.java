import client.NettyClient;
import service.LoginService;
import utils.RedisUtil;
import utils.ZkUtil;

public class ChatClientApplication {
    public static void main(String[] args) {
        startup();
    }

    private static void startup(){
        try {
            // 连接zk
            ZkUtil.connection();
            RedisUtil.connection();
            // 登录
            boolean login = false;
            do{
                login = LoginService.login();
                if(!login){
                    System.out.println("该用户不存在，请重重新登录！");
                }
            }while (!login);
            // 获w取NettyServer连接信息
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
