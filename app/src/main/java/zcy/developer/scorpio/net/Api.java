package zcy.developer.scorpio.net;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author zcy.
 * @date 2018/3/26.
 */

public interface Api {

    @GET("json.shtml")
    Observable<String> getTest(@Query("city") String city);
}
