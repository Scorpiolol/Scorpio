package zcy.developer.scorpiosdk.net.util;

import android.content.Context;

import okhttp3.Interceptor;
import zcy.developer.scorpiosdk.net.interceptor.AddCookiesInterceptor;
import zcy.developer.scorpiosdk.net.interceptor.ReceivedCookiesInterceptor;

/**
 * @author zcy.
 * @date 2018/3/26.
 */

public class InterceptorUils {

    public static Interceptor getAddCookies(Context context) {
        return new AddCookiesInterceptor(context);
    }

    public static Interceptor getAddCookies(Context context, String lang) {
        return new AddCookiesInterceptor(context, lang);
    }

    public static Interceptor getReceivedCookies(Context context) {
        return new ReceivedCookiesInterceptor(context);
    }
}
