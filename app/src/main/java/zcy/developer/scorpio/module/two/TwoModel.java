package zcy.developer.scorpio.module.two;

import zcy.developer.scorpio.config.WeatherConfig;
import zcy.developer.scorpio.entity.WeatherData;
import zcy.developer.scorpio.module.weather.WeatherContract;
import zcy.developer.scorpio.module.weather.WeatherModel;
import zcy.developer.scorpiosdk.net.request.XRequestListener;

/**
 * Two数据处理
 *
 * @author zcy.
 * @date 2018/5/25.
 */

public class TwoModel implements TwoContract.IModel {

    private WeatherContract.IModel iModel;

    @SuppressWarnings("WeakerAccess")
    public TwoModel() {
        iModel = new WeatherModel();
    }

    @Override
    public void getWeatherData(String city, XRequestListener<WeatherData> listener) {
        if (WeatherConfig.CHENGDU.equals(city)) {
            city = WeatherConfig.BEIJING;
        }
        iModel.getWeatherData(city, listener);
    }
}