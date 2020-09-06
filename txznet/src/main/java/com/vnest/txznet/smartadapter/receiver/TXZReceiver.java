package com.vnest.txznet.smartadapter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vnest.txznet.smartadapter.Params;

public class TXZReceiver extends BroadcastReceiver {
    static final String TAG = "TXZReceiver";

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String str = ": ";
        String str2 = ", ";
        if (Params.ACTION_WAKEUP.equals(action)) {
            String str3 = Params.EXTRA_WAKEUP_CMDACC;
            int intExtra = intent.getIntExtra(str3, -1);
            StringBuilder sb = new StringBuilder();
            sb.append(action);
            sb.append(str2);
            sb.append(str3);
            sb.append(str);
            sb.append(intExtra);
            Log.d(TAG, sb.toString());
        } else if (Params.ACTION_NOTIFY_CARPLAY.equals(action)) {
            String str4 = "status";
            String stringExtra = intent.getStringExtra(str4);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(action);
            sb2.append(str2);
            sb2.append(str4);
            sb2.append(str);
            sb2.append(stringExtra);
        }
    }
}
