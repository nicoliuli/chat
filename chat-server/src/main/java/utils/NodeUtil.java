package utils;

import properties.CommonPropertiesFile;
import properties.PropertiesMap;

public class NodeUtil {
    public static String node(String host, Integer port) {
        String node = host + ":" + port;
        return node;
    }

    public static String thisNode(){
        return node(CommonPropertiesFile.getHost(), Integer.parseInt(PropertiesMap.getProperties("port")));
    }
}
