package ai.kitt.vnest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import ai.kitt.vnest.feature.activitymain.MainActivity;
import ai.kitt.vnest.util.common.Config;

public class VoiceDialReceiver extends BroadcastReceiver {
    public VoiceDialReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equalsIgnoreCase(Config.ZOTYE_MCU2APP_ACTION)) {
            onSerialDataReceived(context, intent.getByteArrayExtra(Config.ZOTYE_EXTRA_DATA));
        }
        Toast.makeText(context, "MCU to APp", Toast.LENGTH_SHORT).show();
    }

    private void onSerialDataReceived(Context context, byte[] bArr) {
        if (bArr != null && bArr.length > 5 && bArr[3] == 18 && bArr[5] == 8) {
            onVoiceKey(context);
        }
    }

    private void onVoiceKey(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
