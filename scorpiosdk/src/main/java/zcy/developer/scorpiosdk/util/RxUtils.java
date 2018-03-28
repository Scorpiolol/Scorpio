package zcy.developer.scorpiosdk.util;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;


/**
 * Created by zcy on 2017/8/2.
 * RxJava相关工具类
 */

public class RxUtils {

    public static <T> LifecycleTransformer<T> bindToLifecycle(T view) {
        if (view instanceof LifecycleProvider) {
            return ((LifecycleProvider) view).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }
    }

}
