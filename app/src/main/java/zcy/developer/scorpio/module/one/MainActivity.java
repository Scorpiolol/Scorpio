package zcy.developer.scorpio.module.one;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import zcy.developer.scorpio.R;
import zcy.developer.scorpio.config.ColorConfig;
import zcy.developer.scorpio.entity.WeatherData;
import zcy.developer.scorpio.base.BaseActivity;
import zcy.developer.scorpiosdk.util.RxUtils;

/**
 * @author zcy
 */
public class MainActivity extends BaseActivity<OneContract.IPresenter> implements OneContract.IView {

    private static final String TAG = "MainActivity";

    @BindView(R.id.etWeather)
    AppCompatEditText etWeather;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.btGoTwo)
    Button btGoTwo;
    @BindView(R.id.btRed)
    Button btRed;
    @BindView(R.id.btBlue)
    Button btBlue;

    private OnePresenter presenter;
    private Disposable disposable;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected OneContract.IPresenter initPresenter() {
        return presenter = new OnePresenter(this);
    }

    @Override
    protected void initView() {
        btGoTwo.setText("跳转至第二个");
    }

    @Override
    protected void initListener() {
        btGoTwo.setOnClickListener(this);
        btRed.setOnClickListener(this);
        btBlue.setOnClickListener(this);

        etWeather.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (disposable != null) {
                    disposable.dispose();
                }
                disposable = Observable.timer(1000, TimeUnit.MILLISECONDS)
                        .compose(RxUtils.bindToLifecycle(getActivity()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> presenter.getWeatherData(etWeather.getText().toString()));

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onClickView(View v) {
        switch (v.getId()) {
            case R.id.btGoTwo:
                presenter.goOtherActivity();
                break;

            case R.id.btRed:
                presenter.changeColor(ColorConfig.RED);
                break;

            case R.id.btBlue:
                presenter.changeColor(ColorConfig.BLUE);
                break;

            default:
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getWeatherSuccess(WeatherData data) {
        String temperature = "温度" + data.getData().getWendu() + "\n";
        String tips = "温度" + data.getData().getGanmao() + "\n";
        tvContent.setText(temperature + tips);
    }

    @Override
    public void getWeatherError(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void setContentColor(int resColor) {
        tvContent.setTextColor(resColor);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
