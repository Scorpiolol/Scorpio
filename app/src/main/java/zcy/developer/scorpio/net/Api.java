package zcy.developer.scorpio.net;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import zcy.developer.scorpio.entity.WeatherData;

/**
 * @author zcy.
 * @date 2018/3/26.
 */

public interface Api {

    @GET("json.shtml")
    Observable<WeatherData> getTest(@Query("city") String city);
}
