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
     * 集群会话
     * @param uid
     * @return
     */
    public static String sessionStore(Long uid) {
        return "chat:user:session:" + uid;
    }
}
