package properties;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * 通用的配置文件
 */
public class CommonPropertiesFile {

    // 该节点的host
    public static final String host = getHost();

    /**
     * server端用于获取本节点的host，客户端禁止使用
     * @return
     */
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

    // redis
    public static final String REDIS_HOST = "127.0.0.1";
    public static final Integer REDIS_PORT = 6379;

    // zk
    public static final String ZK_HOST = "127.0.0.1:2181";
}
