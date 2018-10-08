package zcy.developer.scorpiosdk.net.builder;

import android.support.annotation.NonNull;

import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * @author zcy.
 * @date 2018/3/26.
 */

public interface IBuilder {

    interface Value<T> {

        Class<T> api();

        Interceptor[] interceptors();

        Interceptor logInterceptor();

        Map<String, String> defaultHeader();

        Map<String, String> header();

        int connectTime();

        OkHttpClient.Builder builder();

        Converter.Factory factory();

        CallAdapter.Factory adapterFactory();

    }

    interface BaseUrl<T> {

        Api<T> setBaseUrl(@NonNull String baseUrl);
    }

    interface Api<T> {

        XNetworkService<T> create();

        OkHttpClient.Builder getBuilder();

    }

    interface Builder<T> {

        /**
         * 必须设置
         *
         * @param api Api接口
         * @return Api.class
         */
        BaseUrl<T> setApi(@NonNull Class<T> api);

        Builder<T> addInterceptor(Interceptor... interceptors);

        Builder<T> addDefaultHeader(Map<String, String> defaultHeader);

        Builder<T> setLogLevel(boolean isOpenLog, int level);

        Builder<T> addHeader(Map<String, String> header);

        Builder<T> setConnectTimeout(int milliseconds);

        Builder<T> setBuilder(OkHttpClient.Builder builder);

        Builder<T> setConverterFactory(Converter.Factory factory);

        Builder<T> setAdapterFactory(CallAdapter.Factory adapterFactory);

    }

}
