package properties;

import com.alibaba.fastjson.JSON;
import utils.CollectionUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PropertiesMap {
    private static Map<String, String> propMap = new HashMap<>();

    public static void loadProperties() {

        java.util.Properties prop = new java.util.Properties();
        try {
            prop.load(PropertiesMap.class.getClassLoader().getResourceAsStream("application.properties"));
            Set<String> keys = prop.stringPropertyNames();
            if (!CollectionUtil.isEmpty(keys)) {
                Map<String, String> map = new HashMap<>();
                for (String key : keys) {
                    String value = null;
                    if ("host".equals(key)) {
                        value = getHost();
                    } else {
                        value = prop.getProperty(key);
                    }
                    map.put(key, value);
                    propMap = map;
                }
                System.out.println(JSON.toJSONString(propMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            prop.clear();
        }
    }

    private static String getHost() {
        String hostAddress = "";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
            return hostAddress;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostAddress;
    }

    public static String getProperties(String key) {
        if (CollectionUtil.isEmpty(propMap)) {
            return "";
        }
        return propMap.get(key);
    }
}
