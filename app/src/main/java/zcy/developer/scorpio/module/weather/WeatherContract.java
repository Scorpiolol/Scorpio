package zcy.developer.scorpio.module.weather;

import zcy.developer.scorpio.entity.WeatherData;
import zcy.developer.scorpiosdk.base.IBasePresenter;
import zcy.developer.scorpiosdk.base.IBaseView;
import zcy.developer.scorpiosdk.net.request.XRequestListener;

/**
 * WeatherContact协议
 *
 * @author zcy.
 * @date 2018/5/25.
 */

public interface WeatherContract {

    interface IView extends IBaseView {
        void getWeatherSuccess(WeatherData data);

        void getWeatherError(String errorMsg);
    }


    interface IPresenter extends IBasePresenter<IBaseView> {

        void getWeatherData(String city);
    }


    interface IModel {

        void getWeatherData(String city, XRequestListener<WeatherData> listener);

    }


}