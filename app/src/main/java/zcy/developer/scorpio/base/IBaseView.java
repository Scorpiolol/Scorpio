package zcy.developer.scorpio.base;

import android.app.Activity;
import android.content.Context;

/**
 * @author zcy
 * @date 2017/6/28
 * 视图层接口基类
 */

public interface IBaseView
//        extends LifecycleObserver
{

    /**
     * 吐司提示
     *
     * @param msg 消息信息
     */
    void showToast(String msg);

    Context getContext();

    Activity getActivity();

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    void initData();

}
