package utils;

import com.alibaba.fastjson.JSON;
import session.DistributionSession;

public class JsonUtil {
    public static String distubutionSession2Json(DistributionSession session){
        return JSON.toJSONString(session);
    }

    public static DistributionSession json2DistributionSession(String session){
        return JSON.parseObject(session,DistributionSession.class);
    }
}
