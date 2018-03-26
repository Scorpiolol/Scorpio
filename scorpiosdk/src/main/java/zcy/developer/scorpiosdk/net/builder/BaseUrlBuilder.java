package zcy.developer.scorpiosdk.net.builder;

import android.support.annotation.NonNull;

/**
 * @author zcy.
 * @date 2018/3/26.
 */

public class BaseUrlBuilder<T> implements IBuilder.BaseUrl<T> {
    private IBuilder.Value<T> value;

    BaseUrlBuilder(IBuilder.Value<T> value) {
        this.value = value;
    }

    @Override
    public IBuilder.Api<T> setBaseUrl(@NonNull String baseUrl) {
        return new ApiBuilder<>(value, baseUrl);
    }

}
