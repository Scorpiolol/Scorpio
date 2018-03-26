package zcy.developer.scorpiosdk.net.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author zcy.
 * @date 2018/3/26.
 */

public class NetUtils {

    /**
     * 获取用于上传文件的Map
     *
     * @param key   文件Key
     * @param paths 文件路径的数组
     * @return 已封装完成的装载RequestBody的map
     */
    public static Map<String, RequestBody> getFileMap(String key, List<String> paths) {
        Map<String, RequestBody> map = new HashMap<>(paths.size());
        for (int i = 0; i < paths.size(); i++) {
            File file = new File(paths.get(i));
            map.put(key + "\";filename=\"" + file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        return map;
    }
}
