package properties;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class CommonPropertiesFile {
    // NettyServer启动监听端口
    public static final Integer port = 8080;
    // 该节点的host
    public static final String host = getHost();

    public static String getHost() {
        String hostAddress = "";
        try {
            hostAddress = Inet4Address.getLocalHost().getHostAddress();
            return hostAddress;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostAddress;
    }

    public static final String REDIS_HOST = "127.0.0.1";
    public static final Integer REDIS_PORT = 6379;

    public static final String ZK_HOST = "127.0.0.1:2181";
}
