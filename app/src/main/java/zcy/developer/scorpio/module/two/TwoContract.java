package zcy.developer.scorpio.module.two;

import zcy.developer.scorpio.module.one.OneContract;
import zcy.developer.scorpio.module.weather.WeatherContract;
import zcy.developer.scorpio.base.IBasePresenter;
import zcy.developer.scorpio.base.IBaseView;

/**
 * TwoContact协议
 *
 * @author zcy.
 * @date 2018/5/25.
 */

public interface TwoContract {

    interface IView extends IBaseView, OneContract.IView {

    }


    interface IPresenter extends IBasePresenter<IBaseView>, OneContract.IPresenter {

    }


    interface IModel extends WeatherContract.IModel {

    }


}