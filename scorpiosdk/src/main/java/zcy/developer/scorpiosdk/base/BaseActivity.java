package zcy.developer.scorpiosdk.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import zcy.developer.scorpiosdk.util.ToastUtils;

/**
 * @author zcy
 * @date 2017/3/1
 * Activity基类
 */

public abstract class BaseActivity<P extends IBasePresenter<IBaseView>>
        extends RxAppCompatActivity implements View.OnClickListener, IBaseView {

    private static final String TAG = "BaseActivity";

    private View view;
    private Context context;
    private Activity activity;
    protected P presenter;
    /**
     * 是否使用沉浸式状态栏
     */
    private boolean isTitleBarTransparent;
    /**
     * 是否使用EventBus
     */
    private boolean isUseEventBus;
    private int layoutRes;
    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutRes = setLayoutId();
        Log.d(TAG, "onCreate: layoutId==" + setLayoutId());
        if (layoutRes != 0) {
            setContentView(layoutRes);
            view = LayoutInflater.from(this).inflate(layoutRes, null);
            context = this;
            activity = this;
        }

        if (presenter == null) {
            presenter = initPresenter();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initSystemBarTint();
        }
        ButterKnife.bind(this);
        initView();
        initListener();
        getLifecycle().addObserver(this);

        if (useEventBus(isUseEventBus)) {
            Log.d(TAG, "onCreate: 注册EventBus");
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unbindView();
        }
        view = null;
        activity = null;
        context = null;
        presenter = null;
        layoutRes = 0;
        if (useEventBus(isUseEventBus)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected abstract int setLayoutId();

    protected abstract P initPresenter();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void onClickView(View v);


    @Override
    public void onClick(final View v) {
        if (!isFastDoubleClick()) {
            onClickView(v);
        }
    }

    public View getView() {
        if (setLayoutId() != 0) {
            return view;
        }
        return null;
    }


    @Override
    public Context getContext() {
        if (setLayoutId() != 0) {
            return context;
        }
        return null;
    }

    @Override
    public Activity getActivity() {
        if (setLayoutId() != 0) {
            return activity;
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    /**
     * 导航栏透明
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initSystemBarTint() {
        if (translucentStatusBar(isTitleBarTransparent)) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar(boolean isTitleBarTransparent) {
        this.isTitleBarTransparent = isTitleBarTransparent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e("Scorpio", "Build.VERSION_CODES<21");
        }
        return isTitleBarTransparent;
    }

    protected boolean useEventBus(boolean isUse) {
        this.isUseEventBus = isUse;
        return isUseEventBus;
    }


    /**
     * 强制隐藏软键盘
     *
     * @param view 当前焦点View
     */
    @SuppressWarnings("unused")
    protected void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            //强制隐藏键盘
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @SuppressWarnings("unused")
    protected void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.slide_out_from_left, R.anim.slide_out_from_left);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
            // 必不可少，否则所有的组件都不会有TouchEvent了
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
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
