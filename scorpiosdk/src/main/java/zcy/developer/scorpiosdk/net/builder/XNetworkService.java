package zcy.developer.scorpiosdk.net.builder;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import zcy.developer.scorpiosdk.net.config.HeaderConfig;
import zcy.developer.scorpiosdk.net.interceptor.HeaderInterceptor;

/**
 * @author zcy.
 * @date 2018/3/25.
 */
public class XNetworkService<T> {

    private String baseUrl;
    private Class<T> api;
    private ArrayList<Interceptor> interceptors;
    private Map<String, String> defaultHeader;
    private Map<String, String> header;
    private int connectTime = 1000 * 5;
    private OkHttpClient.Builder builder;
    private Converter.Factory factory;
    private CallAdapter.Factory adapterFactory;

    XNetworkService<T> setApi(final Class<T> api) {
        this.api = api;
        return this;
    }

    XNetworkService<T> setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    XNetworkService<T> addInterceptor(Interceptor... interceptors) {
        if (this.interceptors == null) {
            this.interceptors = new ArrayList<>();
        }
        if (interceptors != null && interceptors.length > 0) {
            this.interceptors.addAll(Arrays.asList(interceptors));
        }
        return this;
    }

    XNetworkService<T> addDefaultHeader(Map<String, String> defaultHeader) {
        this.defaultHeader = defaultHeader;
        return this;
    }

    XNetworkService<T> addHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    XNetworkService<T> addConverterFactory(Converter.Factory factory) {
        this.factory = factory;
        return this;
    }

    XNetworkService<T> addCallAdapterFactory(CallAdapter.Factory factory) {
        this.adapterFactory = factory;
        return this;
    }

    XNetworkService<T> setConnectTimeout(int milliseconds) {
        this.connectTime = milliseconds;
        return this;
    }

    XNetworkService<T> setBuilder(OkHttpClient.Builder builder) {
        this.builder = builder;
        return this;
    }

    OkHttpClient.Builder getBuilder() {
        if (builder == null) {
            builder = new OkHttpClient.Builder()
                    .addInterceptor(new HeaderInterceptor()
                            .setDefaultHeader(defaultHeader)
                            .setHeader(header))
                    .connectTimeout(connectTime, TimeUnit.MILLISECONDS);
            addInterceptors(builder, interceptors);
        }
        return builder;
    }

    private T create(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(adapterFactory == null ? RxJava2CallAdapterFactory.create() : adapterFactory)
                .addConverterFactory(factory == null ? FastJsonConverterFactory.create() : factory)
                .baseUrl(baseUrl)
                .build().create(api);
    }

    public T getApi() {
        OkHttpClient.Builder builder = getBuilder();
        return create(builder.build());
    }

    private void addInterceptors(OkHttpClient.Builder builder, ArrayList<Interceptor> interceptors) {
        if (interceptors == null || interceptors.size() <= 0) {
            return;
        }
        for (Interceptor i : interceptors) {
            if (i != null) {
                builder.addInterceptor(i);
            }
        }
    }


    //--------------- 工厂 ---------------

    /**
     * Builder类
     *
     * @param <T> Api接口
     */
    public static class Builder<T> implements IBuilder.Builder<T>, IBuilder.Value<T> {

        private Interceptor[] interceptors;
        private Map<String, String> defaultHeader;
        private Map<String, String> header;
        private int connectTime;
        private OkHttpClient.Builder builder;
        private Class<T> api;
        private Converter.Factory factory;
        private CallAdapter.Factory adapterFactory;

        private HttpLoggingInterceptor.Level mLevel;
        private boolean isOpenLog;

        @Override
        public IBuilder.BaseUrl<T> setApi(@NonNull Class<T> api) {
            this.api = api;
            return new BaseUrlBuilder<>(this);
        }

        @Override
        public IBuilder.Builder<T> addInterceptor(Interceptor... interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        @Override
        public IBuilder.Builder<T> addDefaultHeader(Map<String, String> defaultHeader) {
            this.defaultHeader = defaultHeader;
            return this;
        }


        @Override
        public IBuilder.Builder<T> setLogLevel(boolean isOpenLog, int level) {
            switch (level) {
                case HeaderConfig.BODY:
                    mLevel = HttpLoggingInterceptor.Level.BODY;
                    break;
                case HeaderConfig.BASIC:
                    mLevel = HttpLoggingInterceptor.Level.BASIC;
                    break;
                case HeaderConfig.HEADERS:
                    mLevel = HttpLoggingInterceptor.Level.HEADERS;
                    break;
                case HeaderConfig.NONE:
                    mLevel = HttpLoggingInterceptor.Level.NONE;
                    break;
                default:
                    mLevel = HttpLoggingInterceptor.Level.BODY;
                    break;
            }
            this.isOpenLog = isOpenLog;
            return this;
        }

        @Override
        public IBuilder.Builder<T> addHeader(Map<String, String> header) {
            this.header = header;
            return this;
        }

        @Override
        public IBuilder.Builder<T> setConnectTimeout(int milliseconds) {
            this.connectTime = milliseconds;
            return this;
        }

        @Override
        public IBuilder.Builder<T> setBuilder(OkHttpClient.Builder builder) {
            this.builder = builder;
            return this;
        }

        @Override
        public IBuilder.Builder<T> setConverterFactory(Converter.Factory factory) {
            this.factory = factory;
            return this;
        }

        @Override
        public IBuilder.Builder<T> setAdapterFactory(CallAdapter.Factory adapterFactory) {
            this.adapterFactory = adapterFactory;
            return this;
        }


        @Override
        public Class<T> api() {
            return api;
        }


        @Override
        public Interceptor[] interceptors() {
            return interceptors;
        }

        @Override
        public Interceptor logInterceptor() {
            return isOpenLog ? new HttpLoggingInterceptor(this::createLog).setLevel(mLevel) : null;
        }

        @Override
        public Map<String, String> defaultHeader() {
            return defaultHeader;
        }

        @Override
        public Map<String, String> header() {
            return header;
        }

        @Override
        public int connectTime() {
            return connectTime;
        }

        @Override
        public OkHttpClient.Builder builder() {
            return builder;
        }

        @Override
        public Converter.Factory factory() {
            return factory;
        }

        @Override
        public CallAdapter.Factory adapterFactory() {
            return adapterFactory;
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
}
