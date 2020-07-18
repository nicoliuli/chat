package utils;

import constants.PropertiesFile;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;
import java.util.Random;

public class ZkUtil {
    private static ZooKeeper zk;
    private static final String ZK_PATH = "/Chat";
    private static final String BIZ_PATH = "/NettyServer";

    /**
     * 连接
     */
    public static void connection() throws Exception {

        zk = new ZooKeeper(PropertiesFile.ZK_HOST, 10000, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {

            }
        });

    }

    /**
     * 随机获取server连接
     */
    public static String [] getRandomServer() throws Exception {

        List<String> childrens = zk.getChildren(ZK_PATH + BIZ_PATH, false);
        if(CollectionUtil.isEmpty(childrens)){
            throw new Exception("获取NettyServer信息失败");
        }
        int size = childrens.size();
        String child = childrens.get(new Random().nextInt(size));
        System.out.println("get server is : "+child);
        //ip:port格式
        return child.split(":");

    }

    public static void disConnection(){
        if(zk !=null){
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
