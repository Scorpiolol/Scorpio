package zcy.developer.scorpio.module.two;

import zcy.developer.scorpio.module.one.MainActivity;
import zcy.developer.scorpio.module.one.OneContract;
import zcy.developer.scorpio.module.one.OnePresenter;
import zcy.developer.scorpio.base.BasePresenter;
import zcy.developer.scorpio.base.IBaseView;

/**
 * Two业务逻辑
 *
 * @author zcy.
 * @date 2018/5/25.
 */

public class TwoPresenter extends BasePresenter<IBaseView>
        implements TwoContract.IPresenter {

    @SuppressWarnings("unused")
    private static final String TAG = "TwoPresenter";

    private TwoContract.IModel iModel;
    private TwoContract.IView iView;

    private OneContract.IPresenter onePresenter;

    @SuppressWarnings("WeakerAccess")
    public TwoPresenter(TwoContract.IView iView) {
        super(iView);
        iModel = new TwoModel();
        this.iView = iView;
        onePresenter = new OnePresenter(iView);
    }

    @Override
    public void getWeatherData(String city) {
        onePresenter.getWeatherData(city);
    }

    @Override
    public void goOtherActivity() {
        MainActivity.startActivity(iView.getContext());
        iView.getActivity().finish();
    }

    @Override
    public void changeColor(int type) {
        onePresenter.changeColor(type);
    }
}