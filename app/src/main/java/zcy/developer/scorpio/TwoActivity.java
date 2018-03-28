package zcy.developer.scorpio;

import android.content.Context;
import android.content.Intent;

import zcy.developer.scorpiosdk.base.SimpleActivity;

/**
 * @author zcy.
 * @date 2018/3/28.
 */

public class TwoActivity extends SimpleActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_two;
    }

    @Override
    protected void init() {

    }

    @Override
    public void initData() {

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TwoActivity.class);
        context.startActivity(intent);
    }
}
