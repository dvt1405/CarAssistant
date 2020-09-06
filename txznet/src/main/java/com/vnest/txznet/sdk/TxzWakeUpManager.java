package com.vnest.txznet.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

public class TxzWakeUpManager extends BaseWakeupManager {
    private static final String ACTION_RECEIVER_ONWAKEUP = "txz.intent.action.smartwakeup.onwakeup";
    private static final String ACTION_SERVICE_START_APP = "txz.intent.action.smartwakeup.startApp";
    private static final String ACTION_WAKEUP_CONFIG = "txz.intent.action.smartwakeup.config";
    private static TxzWakeUpManager sInstance = new TxzWakeUpManager();
    private WakeupReceiver mWakeupReceiver;

    class WakeupReceiver extends BroadcastReceiver {
        WakeupReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            TxzWakeUpManager.this.onEvent(intent.getIntExtra(Constants.KEY_CALLBACK, 0), intent.getExtras());
        }
    }

    public static TxzWakeUpManager getInstance() {
        return sInstance;
    }

    private TxzWakeUpManager() {
    }

    /* access modifiers changed from: protected */
    public void initializeEnv() {
        registerReceiver();
        startService();
    }

    /* access modifiers changed from: protected */
    public void sendBroadcast(Bundle bundle) {
        if (this.mContext == null) {
            Log.e("TXZ", "no invoke init, Context == null");
            return;
        }
        Intent intent = new Intent(ACTION_WAKEUP_CONFIG);
        intent.addFlags(32);
        intent.putExtras(bundle);
        this.mContext.sendBroadcast(intent);
    }

    private void startService() {
        if (this.mContext == null) {
            Log.e("TXZ", "no invoke init, Context == null");
            return;
        }
        Intent intent = new Intent(ACTION_SERVICE_START_APP);
        intent.setPackage("com.txznet.txz");
        this.mContext.startService(intent);
    }

    private void registerReceiver() {
        if (this.mWakeupReceiver == null) {
            this.mWakeupReceiver = new WakeupReceiver();
            this.mContext.registerReceiver(this.mWakeupReceiver, new IntentFilter(ACTION_RECEIVER_ONWAKEUP));
            Log.d("TXZ", "register Receiver... ");
        }
    }
}
