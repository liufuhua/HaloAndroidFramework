package common.android.tools;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

/**
 * 获取设备信息.
 */
public final class DeviceUtils {

    private static Point sScreenSize;
    private static final Uri DEVICE_ID_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final String ID_KEY = "android_id";

    /**
     * 获取当前手机的IMEI.
     *
     * @param context 当前系统环境
     * @return 手机的IMEI
     */
    public static String getImei(Context context) {
        if (isGenymotion()) {
            return "Genymotion";
        }
        String imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (TextUtils.isEmpty(imei)) {
            imei = "Unknown";
        }
        return imei;
    }

    /**
     * 获取当前IMSI. 有可能为空.
     *
     * @param context 当前系统环境
     * @return IMSI
     */
    public static String getImsi(Context context) {
        String imsi = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        if (TextUtils.isEmpty(imsi)) {
            imsi = "";
        }
        return imsi;
    }

    /**
     * 获取设备的屏幕大小.
     *
     * @param context 当前环境
     * @return 设备屏幕大小
     */
    public static Point getScreenSize(Context context) {
        if (sScreenSize == null) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            sScreenSize = new Point(dm.widthPixels, dm.heightPixels);
        }
        return sScreenSize;
    }

    /**
     * 获取用户当前设置的语言和地区. 例如zh-CN.
     *
     * @param context 当前系统环境
     * @return 用户当前设置的语言和地区.
     */
    public static String getLocalization(Context context) {
        String lang = context.getResources().getConfiguration().locale.toString();
        return lang.replace('_', '-');
    }

    /**
     * 获取当前国家或地区.
     *
     * @param context 当前系统环境
     * @return 用户当前设置的国家.
     */
    public static String getCountry(Context context) {
        return context.getResources().getConfiguration().locale.getCountry();
    }

    /**
     * 获取用户当前设置的语言.
     *
     * @param context 系统环境
     * @return 用户当前安设置的语言
     */
    public static String getLanguage(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage();
    }

    /**
     * 获取手机型号. 例如ME525
     *
     * @return 手机型号
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机制造商. 例如Motorola
     *
     * @return 手机制造商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取操作系统版本名称，例如4.2.2.
     *
     * @return 操作系统版本名称
     */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取操作系统版本号，例如18
     *
     * @return 操作系统版本编号
     */
    public static int getOsVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机是否被root了.
     *
     * @return 手机是否被root.
     */
    public static boolean isRooted() {
        String command = "a";
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                // nothing
            }
        }
        return true;
    }

    /**
     * 仅用于调试目的.
     *
     * @return 当前设备是否为Genymotion
     */
    public static boolean isGenymotion() {
        return "Genymotion".equalsIgnoreCase(Build.MANUFACTURER);
    }

    /**
     * 获取SIM卡的国家码.
     * @param context Android环境
     * @return 国家码
     */
    public static String getMcc(Context context) {
        String MCCAndMNC = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperator();
        if (MCCAndMNC == null || MCCAndMNC.length() < 3) {
            return "";
        } else {
            return MCCAndMNC.substring(0, 3);
        }
    }

    /**
     * 是否使用日语，排序用
     *
     * @return
     */
    public final static boolean itsJapaUsedHere() {
        String language = Locale.getDefault().getLanguage();
        return language.equals("ja");
    }

    public static boolean isForAmazon(Context context) {
        if ("amazon".equalsIgnoreCase(getCurrentPackageBusinessChannel(context))) {
            return true;
        }
        return false;
    }

    private static String getCurrentPackageBusinessChannel(Context context) {
        String businessChannel = "";
//        String userChannel = "";

        try {
            InputStream input = context.getResources().getAssets().open(
                    "channel.txt");

            String decodeChannel = convertStreamToString(input);

            int SplitIndex = decodeChannel.indexOf('|');
            if (SplitIndex < 0) {
                businessChannel = decodeChannel;
            } else {
                businessChannel = decodeChannel.substring(0, SplitIndex);
//                userChannel = decodeChannel.substring(SplitIndex + 1);
            }
        } catch (IOException e) {
            Log.d("", "read channel text failed.");
        } catch (Exception e) {
            Log.d("", "decode channel text failed.");
        } finally {
        }
        return businessChannel;
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        String line = null;
        if (is != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8192);
                line = reader.readLine();
            } finally {
                is.close();
            }
        }
        return (line == null) ? "" : line;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getGSFId(Context context) {
        String[] params = {ID_KEY};
        Cursor c = context.getContentResolver().query(DEVICE_ID_URI, null, null, params, null);

        if (c == null || !c.moveToFirst() || c.getColumnCount() < 2) {
            return null;
        }

        try {
            return Long.toHexString(Long.parseLong(c.getString(1)));
        } catch (NumberFormatException e) {
            return null;
        } finally {
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getAndroidId(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        return androidId;
    }

    public static String getPhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = tMgr.getLine1Number();
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumber = "";
        }
        return phoneNumber;
    }

    public static String getOsId(Context context) {
        return Build.ID;
    }

    public static String getOsBoard(Context context) {
        return Build.BOARD;
    }

    public static String getWifiMac(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wm != null && wm.getConnectionInfo() != null) {
            return wm.getConnectionInfo().getMacAddress();
        }
        return null;
    }

    public static boolean isAppAtBackground(Context c) {
        ActivityManager am = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(c.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    private DeviceUtils() {
    }
}
