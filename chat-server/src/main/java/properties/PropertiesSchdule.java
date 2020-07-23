package properties;

import com.alibaba.fastjson.JSON;
import utils.CollectionUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PropertiesSchdule {
    public static Map<String, String> propMap = new HashMap<>();

    public static void loadProperties() {
        new ScheduledThreadPoolExecutor(4).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                Properties prop = new Properties();
                try {
                    prop.load(PropertiesSchdule.class.getClassLoader().getResourceAsStream("application.properties"));
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
                }finally {
                    prop.clear();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static String getHost() {
        String hostAddress = "";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
            return hostAddress;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostAddress;
    }
}
