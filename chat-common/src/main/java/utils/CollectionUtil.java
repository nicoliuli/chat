package utils;

import java.util.Collection;
import java.util.Map;

public class CollectionUtil {
    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }
}
