package zcy.developer.scorpiosdk.net.interceptor;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import zcy.developer.scorpiosdk.net.config.HeaderConfig;

/**
 * @author zcy.
 * @date 2017/10/10.
 */

public class HeaderInterceptor implements Interceptor {

    @SuppressWarnings("unused")
    private static final String TAG = "HeaderInterceptor";

    private boolean useDefaultHeader = true;
    private Map<String, String> headerMap;

    public HeaderInterceptor() {

    }

    public HeaderInterceptor(Map<String, String> map) {
        headerMap = map;
    }

    public HeaderInterceptor setDefaultHeader(boolean use) {
        useDefaultHeader = use;
        return this;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        initHeader(builder);
        Log.d("网络请求", "Request Header: \n" + builder.build().headers().toString());
        return chain.proceed(builder.build());
    }


    private void initHeader(Request.Builder builder) {
        if (useDefaultHeader) {
            addHeader(builder, HeaderConfig.getDefaultHeader());
        }
        if (headerMap != null) {
            addHeader(builder, headerMap);
        }
    }

    private void addHeader(Request.Builder builder, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
    }
}
