package zcy.developer.scorpiosdk.net.interceptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 将本地的cookie 追加到请求头中
 * Created by wyt on 2017/8/30.
 */

public class AddCookiesInterceptor implements Interceptor {

    private Context context;
    private String lang;

    public AddCookiesInterceptor(Context context, String lang) {
        super();
        this.context = context;
        this.lang = lang;
    }

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        Observable.just(sharedPreferences.getString("cookie", ""))
                .subscribe(cookie -> {
                    if (cookie.contains("lang=ch")) {
                        cookie = cookie.replace("lang=ch", "lang=" + lang);
                    }
                    if (cookie.contains("lang=en")) {
                        cookie = cookie.replace("lang=en", "lang=" + lang);
                    }
                    //添加cookie
                    builder.addHeader("cookie", cookie);
                });
        return chain.proceed(builder.build());
    }

}
