package zcy.developer.scorpio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import zcy.developer.scorpio.net.NetClient;

/**
 * @author zcy
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetClient.init();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
