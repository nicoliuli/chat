package utils;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import properties.CommonPropertiesFile;

/**
 * zk工具类
 */
public class ZkUtil {
    private static ZooKeeper zk;
    private static final String ZK_PATH = "/Chat";
    private static final String BIZ_PATH = "/NettyServer";


    /**
     * 连接zk，并创建相应的节点
     */
    public static void connection() throws Exception {

        zk = new ZooKeeper(CommonPropertiesFile.ZK_HOST, 10000, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {

            }
        });
        Stat zkPath = null;
        zkPath = zk.exists(ZK_PATH, false);
        if (zkPath == null) {
            zk.create(ZK_PATH, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        }

        Stat bizPath = zk.exists(ZK_PATH + BIZ_PATH, false);
        if (bizPath == null) {
            zk.create(ZK_PATH + BIZ_PATH, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        }

    }

    /**
     * 关闭zk连接
     */
    public static void disConnection() {
        if (zk != null) {
            try {
                zk.close();
                System.out.println("close zk ok");
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 向zk /Chat/NettyServer注册一个NettyServer节点
     *
     * @param host
     * @param port
     */
    public static void registerNettyServerNode(String host, Integer port) {
        String node = NodeUtil.node(host,port);
        try {
            zk.create(ZK_PATH + BIZ_PATH + "/" + node, node.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
