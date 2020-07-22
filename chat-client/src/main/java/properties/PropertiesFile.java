package properties;

/**
 * 配置文件
 */
public class PropertiesFile {

    public static final String REDIS_HOST = "127.0.0.1";
    public static final Integer REDIS_PORT = 6379;

    public static final String ZK_HOST = "127.0.0.1:2181";

    /**
     * 模拟对方的uid，测集群消息
     */
    public static final Long otherUid = 2L;
}
