package zcy.developer.scorpiosdk.net.request;

/**
 * @author zcy.
 * @date 2018/3/28.
 */

public interface XRequestListener<T> {

    void onSuccess(T data);

    void onError(Throwable throwable, String msg);
}