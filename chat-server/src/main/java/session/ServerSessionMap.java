package session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储session
 */
public class ServerSessionMap {
    private static ConcurrentHashMap<Long,ServerSession> sessionMap = new ConcurrentHashMap();


    public static void add(Long uid,ServerSession session){
        sessionMap.put(uid,session);
    }

    public static void removeSession(Long uid){
        sessionMap.remove(uid);
    }

    public static ServerSession getSession(Long uid){
        return sessionMap.get(uid);
    }
}
