package zcy.developer.scorpio;

import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import io.reactivex.Observable;
import zcy.developer.scorpio.net.Api;
import zcy.developer.scorpiosdk.base.IBaseView;
import zcy.developer.scorpiosdk.base.SimpleActivity;
import zcy.developer.scorpiosdk.net.builder.SoNetworkService;
import zcy.developer.scorpiosdk.net.request.SoNetRequest;

/**
 * @author zcy
 */
public class MainActivity extends SimpleActivity implements IBaseView {
    private static final String TAG = "MainActivity";
    @BindView(R.id.bt1)
    Button bt1;

    private XNetworkService service;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        service = new XNetworkService
                .Builder<Api>()
                .setApi(Api.class)
                .setBaseUrl("https://www.sojson.com/open/api/weather/")
                .create();


        bt1.setOnClickListener(v -> {
            TwoActivity.startActivity(getContext());
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        Api api = (Api) service.getApi();
        Observable<String> o = api.getTest("成都");
        XNetRequest.create(o).bindLifecycle(this).start();
    }

    @Override
    public void initData() {
        Log.e(TAG, "initData: ");
    }

}
