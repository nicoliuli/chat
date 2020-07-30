package constans;

public class RedisKey {
    /**
     * 存储user信息
     *
     * @return
     */
    public static String userMapKey() {
        return "chat:user:map";
    }



    /**
     * 群处当前集群的用户会话
     *
     * @param host
     * @param port
     * @return
     */
    public static String getSessionStoreMapKey(String host, Integer port) {
        return "chat:user:session:" + host + ":" + port + ":";
    }
}
