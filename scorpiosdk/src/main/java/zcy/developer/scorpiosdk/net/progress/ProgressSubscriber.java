package zcy.developer.scorpiosdk.net.progress;

import android.app.Activity;
import android.app.ProgressDialog;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import zcy.developer.scorpiosdk.base.IBaseView;
import zcy.developer.scorpiosdk.net.RequestListener;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> implements ProgressCancelListener, Observer<T> {

    private static final String TAG = "ProgressSubscriber";

    private RequestListener<T> requestListener;
    private Disposable disposable;
    private String message;
    private IBaseView iBaseView;
    private Activity activity;
    private ProgressDialog dialog;

    public ProgressSubscriber(RequestListener<T> requestListener, IBaseView iBaseView) {
        this.requestListener = requestListener;
        this.iBaseView = iBaseView;
    }

    public ProgressSubscriber(RequestListener<T> requestListener, Activity activity) {
        this.requestListener = requestListener;
        this.activity = activity;
    }

    private void showProgressDialog() {
        dialog = new ProgressDialog(iBaseView != null ? iBaseView.getContext() : activity);
        dialog.setMessage(message);
        dialog.show();
    }

    private void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     */
    @Override
    public void onError(Throwable e) {
        if (requestListener != null)
            requestListener.onError(e.getMessage());
        dismissProgressDialog();

    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;
        if (requestListener != null) {
            showProgressDialog();
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (requestListener != null) {
            requestListener.onSuccess(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ProgressSubscriber<T> setMessage(String message) {
        this.message = message;
        return this;
    }
}