package zcy.developer.scorpiosdk.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import zcy.developer.scorpiosdk.util.ToastUtils;

/**
 * @author zcy.
 * @date 2018/3/28.
 */

public abstract class SimpleFragment extends RxFragment implements IBaseView {

    protected abstract int setLayoutId();

    protected abstract void init();

    private Unbinder unbinder;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (setLayoutId() != 0) {
            view = inflater.inflate(setLayoutId(), null);
            unbinder = ButterKnife.bind(this, view);
        }
        init();
//        getLifecycle().addObserver(this);
        return view;
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

}
