package zcy.developer.scorpiosdk.util;

import android.widget.Toast;

import zcy.developer.scorpiosdk.App;

/**
 * @author zcy.
 * @date 2018/3/24.
 */
public class ToastUtils {
    private static String oldMsg;
    private static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static App APP;

    public static void init(App application) {
        APP = application;
    }

    /**
     * 全局Toast
     */
    public static void showToast(String s) {
        if (toast == null) {
            //此处需要Application为单例模式
            toast = Toast.makeText(APP, s, Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
//                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        oneTime = twoTime;
    }
}
