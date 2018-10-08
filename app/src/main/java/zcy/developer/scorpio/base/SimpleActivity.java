package zcy.developer.scorpio.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * @author zcy.
 * @date 2018/3/28.
 */

@SuppressWarnings("all")
public abstract class SimpleActivity extends RxAppCompatActivity implements IBaseView {

    protected abstract int setLayoutId();

    protected abstract void init();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutRes = setLayoutId();
        if (layoutRes != 0) {
            setContentView(layoutRes);
        }
        ButterKnife.bind(this);
        init();
//        getLifecycle().addObserver(this);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

}
