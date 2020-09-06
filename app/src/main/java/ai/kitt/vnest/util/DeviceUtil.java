package ai.kitt.vnest.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ai.kitt.vnest.App;
import timber.log.Timber;

public class DeviceUtil {
    static final String ENGINE_DAT = ".vehicle.dat";

    private static final long IN_MB = 1048576L;

    private static final String TYPE_CC1 = "CC1";

    private static final String TYPE_CC2 = "CC2";

    private static final String TYPE_NA = "NAN";

    private static final String TYPE_TPRO = "TPRO";

    private static DeviceUtil instance;

    private DeviceUtil() {
        File file = new File(Environment.getExternalStorageDirectory(), ".vehicle.dat");
        if (!file.exists()) {
            long l = (new Date()).getTime();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(UUID.randomUUID().toString());
            stringBuilder.append("-");
            stringBuilder.append(l);
            write(file, stringBuilder.toString());
        }
    }

    public static DeviceUtil getInstance() {
        if (instance == null)
            instance = new DeviceUtil();
        return instance;
    }

    public static long getTotalMemory() {
        ActivityManager activityManager = (ActivityManager) App.get().getSystemService(Context.ACTIVITY_SERVICE);
//    activityManager.getAppTasks()
        List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();
        for (ActivityManager.AppTask appTask : appTasks) {
            appTask.finishAndRemoveTask();
        }
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return (int) (memoryInfo.totalMem / 1048576L);
    }

    public static long getTotalStorage() {
        return Environment.getExternalStorageDirectory().getTotalSpace() / 1048576L;
    }

    public static String getType(Context paramContext) {
        return !isTeyes() ? "NAN" : (isCC2() ? "CC2" : (isTpro() ? "TPRO" : "CC1"));
    }

    public static boolean isCC2() {
        boolean bool;
        if (isPackageInstalled("com.android.launcher3") && isPackageInstalled("com.android.launcher4") && isPackageInstalled("com.android.launcher8")) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    private static boolean isPackageInstalled(String paramString) {
        PackageManager packageManager = App.get().getPackageManager();
        try {
            packageManager.getPackageInfo(paramString, 128);
            return true;
        } catch (PackageManager.NameNotFoundException s) {
            return false;
        }
    }

    public static boolean isTeyes() {
        boolean bool;
        if (AppUtils.isAppInstalled("com.teyes.carkit") && AppUtils.isAppInstalled("com.syu.camera360")) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    public static boolean isTpro() {
        boolean bool;
        if (isPackageInstalled("com.android.launcher3") && AppUtils.isAppInstalled("com.m401050002.bih")) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    private String read(File paramFile) {
        StringBuilder stringBuilder = new StringBuilder();
//    try {
//      BufferedReader bufferedReader = new BufferedReader(paramFile);
//      FileReader fileReader = new FileReader(paramFile);
//      this(paramFile);
//      this(fileReader);
//      while (true) {
//        String str = bufferedReader.readLine();
//        if (str != null) {
//          stringBuilder.append(str);
//          stringBuilder.append('\n');
//          continue;
//        }
//        break;
//      }
//      bufferedReader.close();
//    } catch (IOException file) {
//      Timber.e(file);
//    }
        return stringBuilder.toString();
    }

    private void write(File paramFile, String paramString) {
//    try {
//      OutputStreamWriter outputStreamWriter = new OutputStreamWriter();
//      FileOutputStream fileOutputStream = new FileOutputStream();
//      this(paramFile);
//      this(fileOutputStream);
//      outputStreamWriter.write(paramString);
//      outputStreamWriter.close();
//    } catch (IOException paramFileEx) {
//      Timber.e("File write failed: %s", new Object[] { paramFileEx.toString() });
//    }
    }

    public long getCreatedTime() {
        File file = new File(Environment.getExternalStorageDirectory(), ".vehicle.dat");
        if (!file.exists())
            return 0L;
        String str = read(file);
        return Long.parseLong(str.substring(str.lastIndexOf("-") + 1)) / 1000L;
    }

    public String getId() {
        String str = read(new File(Environment.getExternalStorageDirectory(), ".vehicle.dat"));
        return str.substring(0, str.lastIndexOf("-"));
    }

    public String getMD5Hash() {
        return EncryptUtils.encryptMD5File2String(new File(Environment.getExternalStorageDirectory(), ".vehicle.dat"));
    }

    public long getModifiedTime() {
        File file = new File(Environment.getExternalStorageDirectory(), ".vehicle.dat");
        try {
            return file.exists() ? (FileUtils.getFileLastModified(file) / 1000L) : 0L;
        } catch (Exception fileEx) {
            Timber.e(fileEx);
            return 0L;
        }
    }
}
