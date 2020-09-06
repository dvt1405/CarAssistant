package com.vnest.txznet.smartadapter;

import android.app.Service;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.util.Log;

public class StartService extends Service {
    static final String TAG = "StartService";

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        StringBuilder sb = new StringBuilder();
        sb.append("onCreate android_sdk: ");
        sb.append(VERSION.SDK_INT);
        Log.d(TAG, sb.toString());
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("onStartCommand: ");
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        Log.d(TAG, sb.toString());
        return 1;
    }
}
