package session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import model.domain.User;

/**
 * 服务端session
 */
public class ServerSession {
    private  static final AttributeKey<ServerSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");
    private User user;
    private Channel channel;

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

    public void bind(){
        this.channel.attr(SESSION_KEY).set(this);
        ServerSessionMap.add(this.user.getUid(),this);
    }
}
