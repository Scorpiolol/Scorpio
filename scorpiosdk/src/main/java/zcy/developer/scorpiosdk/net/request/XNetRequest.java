package zcy.developer.scorpiosdk.net.request;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * @author zcy.
 * @date 2018/3/27.
 */

public class XNetRequest {

    public static <T> Request<T> create(Observable<T> observable) {
        observable.debounce(500, TimeUnit.MILLISECONDS);
        return new Request<>(observable);
    }

}
