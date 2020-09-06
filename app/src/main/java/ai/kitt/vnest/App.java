package ai.kitt.vnest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import ai.kitt.snowboy.service.TriggerBroadCast;
import ai.kitt.snowboy.service.TriggerOfflineService;
import ai.kitt.vnest.feature.activitymain.MainActivity;
import kun.kt.opencam.air.AirControl;
import kun.kt.opencam.ipc.ITransitService;

@SuppressLint("LogNotTimber")
public class App extends Application implements Application.ActivityLifecycleCallbacks {
    public static final String CAM_PACKAGE_NAME = "com.syu.camera360";
    public static final String AIR_PACKAGE_NAME = "com.tpms3";
//    public static boolean isActivated = false;
    public static boolean isActivated = false;
    public static boolean isForTest = true;
    private int activityReferences = 0;
    public boolean isInBackground = false;

    private static App INSTANCE;

    public static App get() {
        return INSTANCE;
    }

    public App() {
        INSTANCE = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    public static ITransitService ipcService;
    private static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ipcService = ITransitService.Stub.asInterface(service);
            AirControl.initialize(ipcService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void bindService() {
        Intent intent = new Intent("com.syu.sha.TransitService");
        intent.setPackage("kun.kt.opencam");
        App.get().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void open(String packageName, String className) {
        try {
            Intent intent;
            if (className == null || className.isEmpty()) {
                intent = getPackageManager().getLaunchIntentForPackage(packageName);
            } else {
                intent = new Intent();
                intent.setComponent(new ComponentName(packageName, className));
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void closeApp(String packageName) {
        try {
            ipcService.closeApp(packageName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        TriggerOfflineService.stopService(this);
        isInBackground = false;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }


    @Override
    public void onActivityStopped(Activity activity) {
        Log.e("Activity", "Stoped" + activity.getLocalClassName());
        if (activity instanceof MainActivity) {
            isInBackground = true;
            TriggerOfflineService.stopService(this);
            TriggerOfflineService.startService(this, true);
            TriggerOfflineService.keyStartService = TriggerOfflineService.WAKE_UP;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }


    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e("onActivityDestroyed", activity.getClass().getName());
        Intent intent = new Intent();
        intent.setAction(TriggerBroadCast.ACTION_RESTART_SERVICE);
        sendBroadcast(intent);
    }
}
