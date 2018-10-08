package zcy.developer.scorpio.module.one;

import zcy.developer.scorpio.module.weather.WeatherContract;
import zcy.developer.scorpio.base.IBasePresenter;
import zcy.developer.scorpio.base.IBaseView;

/**
 * OneContact协议
 *
 * @author zcy.
 * @date 2018/5/25.
 */

public interface OneContract {

    interface IView extends IBaseView, WeatherContract.IView {

        void setContentColor(int resColor);
    }


    interface IPresenter extends IBasePresenter<IBaseView>, WeatherContract.IPresenter {

        void goOtherActivity();

        void changeColor(int type);
    }

}