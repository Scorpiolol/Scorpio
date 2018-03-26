package zcy.developer.scorpio.net;

import zcy.developer.scorpiosdk.net.builder.NetworkService;

/**
 * @author zcy.
 * @date 2018/3/26.
 */

public class NetClient {

    public static void init() {
        NetworkService service = new NetworkService
                .Builder<Api>()
                .setApi(Api.class)
                .setBaseUrl("")
                .create();

        new NetworkService.Builder<Api>()
                .setApi(Api.class)
                .setBaseUrl("");
    }
}
