package zcy.developer.scorpio.module.one;

import android.text.TextUtils;

import zcy.developer.scorpio.R;
import zcy.developer.scorpio.config.ColorConfig;
import zcy.developer.scorpio.module.two.TwoActivity;
import zcy.developer.scorpio.module.weather.WeatherContract;
import zcy.developer.scorpio.module.weather.WeatherPresenter;
import zcy.developer.scorpiosdk.base.BasePresenter;
import zcy.developer.scorpiosdk.base.IBaseView;

/**
 * One业务逻辑
 *
 * @author zcy.
 * @date 2018/5/25.
 */

public class OnePresenter extends BasePresenter<IBaseView>
        implements OneContract.IPresenter {

    @SuppressWarnings("unused")
    private static final String TAG = "OnePresenter";

    private OneContract.IView iView;

    private WeatherContract.IPresenter weatherPresenter;

    @SuppressWarnings("WeakerAccess")
    public OnePresenter(OneContract.IView iView) {
        super(iView);
        this.iView = iView;
        weatherPresenter = new WeatherPresenter(iView);
    }

    @Override
    public void getWeatherData(String city) {
        if (!TextUtils.isEmpty(city)) {
            iView.showToast(city);
        }
        weatherPresenter.getWeatherData(city);
    }

    @Override
    public void goOtherActivity() {
        TwoActivity.startActivity(iView.getContext());
        iView.getActivity().finish();
    }

    @Override
    public void changeColor(int type) {
        int resColor;
        switch (type) {
            case ColorConfig.RED:
                resColor = iView.getActivity().getResources().getColor(R.color.colorAccent);
                break;
            case ColorConfig.BLUE:
                resColor = iView.getActivity().getResources().getColor(R.color.colorPrimary);
                break;
            default:
                resColor = iView.getActivity().getResources().getColor(R.color.black);
                break;
        }
        iView.setContentColor(resColor);
    }
}