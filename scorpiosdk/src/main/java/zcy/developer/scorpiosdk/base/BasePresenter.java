package zcy.developer.scorpiosdk.base;

import android.support.annotation.UiThread;
import android.util.Log;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author zcy.
 * @date 2017/6/29.
 */

public abstract class BasePresenter<V> implements IBasePresenter<V> {

    private static final String TAG = "BasePresenter";

    private Reference<V> rfView;
    protected V iView;

    public BasePresenter(V iView) {
        bindView(iView);
    }

    /**
     * 将视图与Presenter绑定
     */
    @UiThread
    @Override
    public void bindView(V iView) {
        this.rfView = new WeakReference<>(iView);
        this.iView = rfView.get();
    }

    /**
     * 如果视图被销毁或回收，则调用该方法
     */
    @UiThread
    @Override
    public void unbindView() {
        if (isViewAttached()) {
            if (rfView != null) {
                rfView.clear();
                rfView = null;
                iView = null;
                Log.d(TAG, "unbindView: 释放IView接口");
            }
        }
    }

    private boolean isViewAttached() {//判断是否与View建立了关联
        return rfView != null && rfView.get() != null;
    }

}
