package zcy.developer.scorpio.util;

import android.app.Activity;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;

import zcy.developer.scorpio.base.IBaseView;


/**
 * Created by zcy on 2017/8/2.
 * RxJava相关工具类
 */

public class RxUtils {

    public static <T> LifecycleTransformer<T> bindToLifecycle(IBaseView view) {
        if (view instanceof LifecycleProvider) {
            return ((LifecycleProvider) view).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }

    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(Activity view) {
        if (view instanceof LifecycleProvider) {
            return ((LifecycleProvider) view).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }

    }
}
