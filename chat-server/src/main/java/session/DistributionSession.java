package session;

import properties.CommonPropertiesFile;
import properties.PropertiesMap;

/**
 * 分布式session
 */
public class DistributionSession {
    private String host;
    private Integer port;
    /**
     * sessionId，用于标志唯一的session
     */
    private String sessionId;
    private Long uid;

    public DistributionSession(String host, Integer port, Long uid,String sessionId) {
        this.host = host;
        this.port = port;
        this.uid = uid;
        this.sessionId = sessionId;
    }

    public DistributionSession(Long uid,String sessionId) {
        this.uid = uid;
        this.host = CommonPropertiesFile.getHost();
        this.port = Integer.parseInt(PropertiesMap.getProperties("port"));
        this.sessionId = sessionId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 组装成host+port信息
     * @return
     */
    public String buildNodeStr() {
        return this.host + this.port;
    }
}
