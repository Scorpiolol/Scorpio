package zcy.developer.scorpio.module.weather;

import zcy.developer.scorpio.entity.WeatherData;
import zcy.developer.scorpiosdk.base.BasePresenter;
import zcy.developer.scorpiosdk.base.IBaseView;
import zcy.developer.scorpiosdk.net.request.XRequestListener;

/**
 * Weather业务逻辑
 *
 * @author zcy.
 * @date 2018/5/25.
 */

public class WeatherPresenter extends BasePresenter<IBaseView>
        implements WeatherContract.IPresenter {

    @SuppressWarnings("unused")
    private static final String TAG = "WeatherPresenter";

    private WeatherContract.IModel iModel;
    private WeatherContract.IView iView;

    @SuppressWarnings("WeakerAccess")
    public WeatherPresenter(WeatherContract.IView iView) {
        super(iView);
        iModel = new WeatherModel();
        this.iView = iView;
    }

    @Override
    public void getWeatherData(String city) {
        XRequestListener<WeatherData> listener = new XRequestListener<WeatherData>() {
            @Override
            public void onSuccess(WeatherData data) {
                iView.getWeatherSuccess(data);
            }

            @Override
            public void onError(Throwable throwable, String msg) {
                iView.getWeatherError(msg);
            }
        };

        iModel.getWeatherData(city, listener);
    }
}