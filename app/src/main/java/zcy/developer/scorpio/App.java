package zcy.developer.scorpio;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDexApplication;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;


/**
 * @author zcy.
 * @date 2018/3/24.
 */
public class App extends MultiDexApplication {
    private static final String TAG = "App";

    private static App INSTANCE;
    private static boolean NETWORK = true;
    private Disposable dis;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        if (NETWORK && dis != null) {
            dis.dispose();
        } else {
            checkNetwork();
        }
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public static boolean getNetworkStatus() {
        return NETWORK;
    }

    private void checkNetwork() {
        dis = Flowable.interval(500, 500, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> NETWORK = isNetworkAvailable());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                return info.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }
}
