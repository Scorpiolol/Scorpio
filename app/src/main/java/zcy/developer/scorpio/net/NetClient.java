package zcy.developer.scorpio.net;

import android.util.Log;

import io.reactivex.Observable;
import zcy.developer.scorpiosdk.net.builder.NetworkService;
import zcy.developer.scorpiosdk.net.util.NetUtils;

/**
 * @author zcy.
 * @date 2018/3/26.
 */

public class NetClient {

    public static void init() {
        NetworkService service = new NetworkService
                .Builder<Api>()
                .setApi(Api.class)
                .setBaseUrl("https://www.sojson.com/open/api/weather/")
                .create();

        Api api = (Api) service.getApi();
        Observable<String> o = api.getTest("成都");
        NetUtils.startRequest(o, s -> Log.d("测试", "accept: s==" + s));
    }
}
