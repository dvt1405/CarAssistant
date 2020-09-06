package ai.kitt.snowboy.service;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

public class TriggerBroadCast extends BroadcastReceiver {
    public final static String ACTION_TURN_MIC_ON = "turn on";
    public final static String ACTION_TURN_MIC_OFF = "turn off";
    public final static String ACTION_START_APP = "start_app";
    private final static String TAG = "Trigger broadcast";
    public final static String ACTION_RESTART_SERVICE = "restart_service";
    private OnHandleTrigger onHandleTrigger;
    private Class<?> activity;

    public static TriggerBroadCast initBroadCast(Context context, OnHandleTrigger onHandleTrigger, Class<?> activity) {
        TriggerBroadCast triggerBroadCast = new TriggerBroadCast(onHandleTrigger, activity);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TURN_MIC_ON);
        intentFilter.addAction(ACTION_TURN_MIC_OFF);
        intentFilter.addAction(ACTION_START_APP);
        intentFilter.addAction(ACTION_RESTART_SERVICE);
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(triggerBroadCast, intentFilter);
        return triggerBroadCast;
    }

    public static void unregisterBroadCast(Context context, TriggerBroadCast triggerBroadCast) {
        if (triggerBroadCast != null) {
            context.unregisterReceiver(triggerBroadCast);
        }
    }

    public TriggerBroadCast(OnHandleTrigger onHandleTrigger, Class<?> activity) {
        this.onHandleTrigger = onHandleTrigger;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        assert action != null;
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo networkInfo = connMgr
                .getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected()) {
            onHandleTrigger.onNetWorkAvailable();
        }

        if (action.equals(ACTION_TURN_MIC_ON)) {
            Log.e(TAG, "Turn on mic");
            onHandleTrigger.onActionTurnOn();
            return;
        }
        if (action.equals(ACTION_START_APP)) {
            Log.e(TAG, "Start app from broadcast");
            try {
                PackageManager pm = context.getPackageManager();
                Intent launchIntent = pm.getLaunchIntentForPackage("ai.kitt.snowboy");
                assert launchIntent != null;
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(launchIntent);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return;
        }
        if (action.equals(ACTION_TURN_MIC_OFF)) {
            Log.e(TAG, "Turn off mic");
            onHandleTrigger.onActionTurnOff();
            return;
        }
        if (action.equals(ACTION_RESTART_SERVICE)) {
            if (!TriggerOfflineService.isServiceRunning) {
                TriggerOfflineService.startService(context, true);
            }
        }
    }

    public interface OnHandleTrigger {
        void onActionTurnOn();

        void onActionTurnOff();

        void onActionStartApp();

        void onNetWorkAvailable();
    }

}
