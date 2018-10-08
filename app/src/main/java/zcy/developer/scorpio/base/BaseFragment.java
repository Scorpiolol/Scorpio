package zcy.developer.scorpio.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author zcy
 * @date 2016/10/20
 * Fragment基类s
 */

public abstract class BaseFragment<P extends IBasePresenter<IBaseView>>
        extends RxFragment implements View.OnClickListener, IBaseView {

    private static final String TAG = "BaseFragment";

    private View view;
    public FragmentActivity mContext;

    protected P presenter;
    private int layoutRes;
    private long lastClickTime;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getLifecycle().addObserver(this);
        mContext = getActivity();
        if (setLayoutId() != 0) {
            layoutRes = setLayoutId();
            view = inflater.inflate(layoutRes, null);
            unbinder = ButterKnife.bind(this, view);
        }

        if (presenter == null) {
            presenter = initPresenter();
        }
        initView();
        initData();
        initListener();
        return view;
    }


    protected abstract P initPresenter();

    protected abstract int setLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void onClickView(View v);

    @Override
    public void onClick(final View v) {
        if (!isFastDoubleClick()) {
            onClickView(v);
        }
    }


    @Override
    public View getView() {
        return this.view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unbindView();
        }
        view = null;
        presenter = null;
        layoutRes = 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    /**
     * 限制0.5秒内按钮只能点击一次
     *
     * @return false=没有连击
     * true=连击
     */
    private boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
