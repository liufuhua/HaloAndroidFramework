package hollo.airport.bus.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 包工具函数集. 这里的Package和App基本认为是同一个概念.
 */
public final class PackageUtils {

    /**
     * 获取Context所在应用的名称.
     * @param context Android环境
     * @return 应用名称
     */
    public static CharSequence getAppName(Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.applicationInfo.loadLabel(context.getPackageManager());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return context.getPackageName();
    }

    /**
     * 获取Context所在应用的版本名称.
     * @param context Android环境
     * @return 应用版本名称.
     */
    public static CharSequence getAppVersion(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    /**
     * 获取Context所在应用的版本号.
     * @param context Android环境
     * @return 应用版本号.
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断某个App是否在手机上已经安装了.
     * @param context Android环境
     * @param packageName 程序包名
     * @return 是否安装
     */
    public static boolean isInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private PackageUtils() {
    }
}
