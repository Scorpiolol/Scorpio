package zcy.developer.scorpiosdk.net.builder;

import android.text.TextUtils;

import okhttp3.OkHttpClient;

/**
 * @author zcy.
 * @date 2018/3/26.
 */

public class ApiBuilder<T> implements IBuilder.Api<T> {

    private String baseUrl;
    private IBuilder.Value<T> value;

    ApiBuilder(IBuilder.Value<T> value, String baseUrl) {
        this.value = value;
        this.baseUrl = baseUrl;

    }

    @Override
    public XNetworkService<T> create() {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("baseUrl is empty");
        }
        if (value.api() == null) {
            throw new NullPointerException("api is null");
        }
        return initNetWorkService();
    }

    @Override
    public OkHttpClient.Builder getBuilder() {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new NullPointerException("baseUrl is empty");
        }
        if (value.api() == null) {
            throw new NullPointerException("api is null");
        }
        return initNetWorkService().getBuilder();
    }

    private XNetworkService<T> initNetWorkService() {
        return new XNetworkService<T>()
                .setBaseUrl(baseUrl)
                .setApi(value.api())
                .addInterceptor(value.interceptors())
                .addHeader(value.header())
                .setBuilder(value.builder())
                .addDefaultHeader(value.defaultHeader())
                .setConnectTimeout(value.connectTime())
                .addCallAdapterFactory(value.adapterFactory())
                .addConverterFactory(value.factory())
                .setLogLevel(value.level());
    }
}
