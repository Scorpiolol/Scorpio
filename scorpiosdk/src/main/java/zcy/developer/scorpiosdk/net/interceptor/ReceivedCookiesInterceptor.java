package zcy.developer.scorpiosdk.net.interceptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.Response;
import zcy.developer.scorpiosdk.App;

/**
 * 将http返回的cookie存储到本地
 * Created by wyt on 2017/8/30.
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    private SharedPreferences sharedPreferences;

    public ReceivedCookiesInterceptor() {
        super();
        sharedPreferences = App.getInstance().getSharedPreferences("cookie", Context.MODE_PRIVATE);
    }

    @SuppressLint("CheckResult")
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("set-cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.fromIterable(originalResponse.headers("set-cookie"))
                    .map(s -> {
                        String[] cookieArray = s.split(";");
                        return cookieArray[0];
                    })
                    .subscribe(cookie -> cookieBuffer.append(cookie).append(";"));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookieBuffer.toString());
            editor.apply();
        }

        return originalResponse;
    }
}
