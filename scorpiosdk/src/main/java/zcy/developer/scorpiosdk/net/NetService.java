package zcy.developer.scorpiosdk.net;

import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import zcy.developer.scorpiosdk.net.config.HttpConfig;
import zcy.developer.scorpiosdk.net.factory.GsonDConverterFactory;

/**
 * @author zcy.
 * @date 2018/3/25.
 */
public class NetService {
    private static final String TAG = "NetService";

    private Api getApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(this::createLog)
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonDConverterFactory.create())
                .baseUrl(HttpConfig.BASE_URL)
                .build()
                .create(Api.class);
    }

    private Api getImageApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(this::createLog)
                        .setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonDConverterFactory.create())
                .baseUrl(HttpConfig.BASE_URL)
                .build()
                .create(Api.class);
    }

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


    private void createLog(String veryLongString) {
        int maxLogSize = 1000;
        for (int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > veryLongString.length() ? veryLongString.length() : end;
            Log.d("网络请求", veryLongString.substring(start, end));
        }
    }
}
