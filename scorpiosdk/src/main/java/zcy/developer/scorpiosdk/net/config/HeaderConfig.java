package zcy.developer.scorpiosdk.net.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zcy.
 * @date 2018/3/24.
 */
public class HeaderConfig {

    public static Map<String, String> getDefaultHeader() {
        Map<String, String> map = new HashMap<>();
        map.put("charset", "UTF-8");
        return map;
    }
}
