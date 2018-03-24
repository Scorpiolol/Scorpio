package zcy.developer.scorpiosdk.net.factory;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import zcy.developer.scorpiosdk.net.factory.entity.CommonBean;
import zcy.developer.scorpiosdk.net.factory.entity.HttpResult;

/**
 * Created by zcy on 2017/8/8.
 * Gson自定义返回结果类
 */

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /**
     * 针对数据返回成功、错误不同类型字段处理
     */
    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        String response = value.string();
        try {
            HttpResult result = gson.fromJson(response, HttpResult.class);
            int code = result.getCode();
            if (code == 0) {
                return gson.fromJson(response, type);
            } else {
                Log.d("HttpManager", "错误信息：" + response);
                CommonBean errResponse = gson.fromJson(response, CommonBean.class);
                throw new ResultException(errResponse.getMessage(), code);
            }
        } finally {
            value.close();
        }
    }
}
