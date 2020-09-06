package ai.kitt.vnest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ai.kitt.snowboy.service.TriggerBroadCast;
import ai.kitt.snowboy.service.TriggerOfflineService;
import ai.kitt.vnest.feature.activitymain.MainActivity;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        assert action != null;
        if(action.equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            TriggerOfflineService.startService(context,true);
        }
    }
}
