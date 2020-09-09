package ai.kitt.vnest.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

import ai.kitt.vnest.App;

public class AppUtil {
    public static boolean isAppAlive(String str) {
        ActivityManager activityManager = (ActivityManager) App.get().getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List runningAppProcesses = activityManager.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            if (((ActivityManager.RunningAppProcessInfo) runningAppProcesses.get(i)).processName.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
