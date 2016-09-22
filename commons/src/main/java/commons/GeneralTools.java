package hollo.airport.bus.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by orson on 16/9/8.
 */
public class GeneralTools {

    public static int formatColorFromHexString(String hexColorString24bits){
        return 0xff000000 | Integer.decode(hexColorString24bits);
    }

    /**
     * 获取当前机器的屏幕密度
     * @param activity
     * @return
     */
    public static int getDensityDpi(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int densityDpi = dm.densityDpi;
        return densityDpi;
    }
}
