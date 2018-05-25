package zcy.developer.scorpio.module.weather;

import io.reactivex.Observable;
import zcy.developer.scorpio.entity.WeatherData;
import zcy.developer.scorpio.net.Api;
import zcy.developer.scorpiosdk.net.builder.XNetworkService;
import zcy.developer.scorpiosdk.net.request.XNetRequest;
import zcy.developer.scorpiosdk.net.request.XRequestListener;

/**
 * Weather数据处理
 *
 * @author zcy.
 * @date 2018/5/25.
 */

public class WeatherModel implements WeatherContract.IModel {

    private XNetworkService apiService;

    @SuppressWarnings("WeakerAccess")
    public WeatherModel() {
        apiService = new XNetworkService
                .Builder<Api>()
                .setApi(Api.class)
                .setBaseUrl("https://www.sojson.com/open/api/weather/")
                .create();
    }


    @Override
    public void getWeatherData(String city, XRequestListener<WeatherData> listener) {
        Api api = (Api) apiService.getApi();
        Observable<WeatherData> o = api.getTest(city);
        XNetRequest.create(o)
                .startByMain(listener);
    }
}