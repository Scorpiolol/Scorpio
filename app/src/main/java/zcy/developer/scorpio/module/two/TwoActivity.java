package zcy.developer.scorpio.module.two;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import zcy.developer.scorpio.R;
import zcy.developer.scorpio.config.ColorConfig;
import zcy.developer.scorpio.config.WeatherConfig;
import zcy.developer.scorpio.entity.WeatherData;
import zcy.developer.scorpio.base.BaseActivity;

/**
 * @author zcy.
 * @date 2018/3/28.
 */

public class TwoActivity extends BaseActivity<TwoContract.IPresenter>
        implements TwoContract.IView {

    @BindView(R.id.btGoTwo)
    Button btGoTwo;
    @BindView(R.id.btRed)
    Button btRed;
    @BindView(R.id.btBlue)
    Button btBlue;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.etWeather)
    AppCompatEditText etWeather;

    private TwoContract.IPresenter presenter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected TwoContract.IPresenter initPresenter() {
        return presenter = new TwoPresenter(this);
    }

    @Override
    protected void initView() {
        etWeather.setText(WeatherConfig.BEIJING);
        btGoTwo.setText("跳转至第一个");
    }

    @Override
    protected void initListener() {
        btGoTwo.setOnClickListener(this);
        btRed.setOnClickListener(this);
        btBlue.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenter.getWeatherData(etWeather.getText().toString());
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

    @Override
    public void setContentColor(int resColor) {
        tvContent.setTextColor(getResources().getColor(R.color.white));
        tvContent.setBackgroundColor(resColor);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getWeatherSuccess(WeatherData data) {
        tvContent.setText("PM2.5:" + data.getData().getPm25());
    }

    @Override
    public void getWeatherError(String errorMsg) {
        showToast(errorMsg);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TwoActivity.class);
        context.startActivity(intent);
    }

}
