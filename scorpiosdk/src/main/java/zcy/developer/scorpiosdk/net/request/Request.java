package zcy.developer.scorpiosdk.net.request;

import android.app.Activity;
import android.app.Fragment;

import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import zcy.developer.scorpiosdk.base.IBaseView;
import zcy.developer.scorpiosdk.util.RxUtils;

/**
 * @author zcy.
 * @date 2018/3/27.
 */

public class Request<T> {
    private Observable<T> observable;

    Request(Observable<T> o) {
        observable = o;
    }

    public <V> Request<T> bindLifecycle(V view) {
        if (view instanceof Activity || view instanceof Fragment || view instanceof IBaseView) {
            if (view instanceof LifecycleProvider) {
                observable.compose(RxUtils.bindToLifecycle(view));
            } else {
                throw new IllegalArgumentException(
                        "view not extends BaseActivity or BaseFragment," +
                                "you can also extends RxAppCompatActivity or RxFragment");
            }
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }
        return this;
    }

    public Observable<T> getObservable() {
        if (observable == null) {
            throw new NullPointerException("Observable is null");
        }
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    public void start() {
        getObservable()
                .subscribe();
    }

    public void startNonMainThread(SoRequestListener<T> listener) {
        getObservable()
                .observeOn(Schedulers.io())
                .subscribe(listener::onSuccess,
                        throwable -> listener.onError(throwable, throwable.getMessage()));
    }

    public void start(SoRequestListener<T> listener) {
        getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onSuccess,
                        throwable -> listener.onError(throwable, throwable.getMessage()));
    }


}
