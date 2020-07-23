package properties;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class PropertiesFile {
    // NettyServer启动监听端口
    public static final Integer port = 8080;

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


}
