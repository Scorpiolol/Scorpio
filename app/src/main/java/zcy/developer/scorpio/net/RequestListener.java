package zcy.developer.scorpio.net;

import android.annotation.SuppressLint;
import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import zcy.developer.scorpio.base.IBaseView;
import zcy.developer.scorpio.net.factory.ResultException;
import zcy.developer.scorpio.net.progress.ProgressSubscriber;
import zcy.developer.scorpio.util.RxUtils;

/**
 * @author zcy.
 * @date 2017/8/7.
 */

public abstract class RequestListener<T> {

    @SuppressWarnings("unused")
    private static final String TAG = "RequestListener";

    /**
     * 成功回调
     *
     * @param t 解析泛型
     */
    public abstract void onSuccess(T t);

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    public abstract void onError(String errorMsg);

    private IBaseView iBaseView;

    protected RequestListener(IBaseView iBaseView) {
        this.iBaseView = iBaseView;
    }

    /**
     * 请求成功
     */
    private void requestSuccess(T t) {
        onSuccess(t);
    }

    /**
     * 请求失败
     */
    private void requestError(Throwable throwable) {
        if (throwable instanceof ResultException) {
            ResultException e = (ResultException) throwable;
            onError(e.getErrMsg());
        } else if (throwable instanceof ConnectException) {
            onError("连接超时，请检查网络设置");
        } else if (throwable instanceof SocketTimeoutException) {
            onError("连接超时，请检查网络设置");
        } else if (throwable instanceof UnknownHostException) {
            onError("没有网络，请检查网络设置");
        } else {
            onError(throwable.getMessage());
        }
        try {
            Log.d("网络请求错误", throwable.getMessage());
        } catch (Exception ignored) {

        }
    }


    /**
     * 发起网络请求
     */
    @SuppressLint("CheckResult")
    public void request(Observable<T> o) {
        o.debounce(500, TimeUnit.MILLISECONDS)
                .compose(RxUtils.bindToLifecycle(iBaseView))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::requestSuccess, this::requestError);
    }

    /**
     * 创建带加载进度的网络请求
     */
    public void requestWithProgress(Observable<T> o) {
        o.debounce(500, TimeUnit.MILLISECONDS)
                .compose(RxUtils.bindToLifecycle(iBaseView))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<>(this, iBaseView));
    }


}
