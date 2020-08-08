package session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import model.domain.User;

import java.util.UUID;

/**
 * 服务端session
 */
public class ServerSession {
    private  static final AttributeKey<ServerSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");
    private User user;
    private Channel channel;
    /**
     * 会话session，用户和DistributionSession绑定
     */
    private String sessionId = UUID.randomUUID().toString();


    public ServerSession(User user, Channel channel) {
        this.user = user;
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ServerSession bind(){
        this.channel.attr(SESSION_KEY).set(this);
        ServerSessionMap.add(this.user.getUid(),this);
        return this;
    }
}
