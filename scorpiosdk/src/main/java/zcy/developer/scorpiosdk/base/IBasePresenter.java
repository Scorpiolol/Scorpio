package zcy.developer.scorpiosdk.base;

/**
 * @author zcy.
 * @date 2017/7/21.
 */

public interface IBasePresenter<V> {

    /**
     * 绑定Presenter与View的生命周期
     *
     * @param iBaseView 视图层接口
     */
    void bindView(V iBaseView);

    /**
     * 取消绑定
     */
    void unbindView();

}
