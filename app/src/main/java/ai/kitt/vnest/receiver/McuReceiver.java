package ai.kitt.vnest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ai.kitt.vnest.util.ACUtil;
import ai.kitt.vnest.util.StringUtil;
import timber.log.Timber;

public class McuReceiver extends BroadcastReceiver {
    private void onGetCarInfo() {
        ACUtil.getInstance().getStatus();
    }

    public McuReceiver() {

    }

    public void onReceive(Context paramContext, Intent paramIntent) {
        String str = paramIntent.getAction();
        if (str != null && str.equalsIgnoreCase("com.jsbd.serial.mcutoapp"))
            onSerialReceived(paramIntent.getByteArrayExtra("Data"));
    }

    void onSerialReceived(byte[] paramArrayOfByte) {
        if (paramArrayOfByte[3] == 18) {
            if (paramArrayOfByte[5] != 1 && paramArrayOfByte[5] == 8) {
                Timber.e("voice key... %s", StringUtil.fromByteArray(paramArrayOfByte));
                onGetCarInfo();
            }
        } else if (paramArrayOfByte[3] == 6) {
            if (paramArrayOfByte[5] == 86) {
                byte b1 = paramArrayOfByte[7];
                byte b2 = paramArrayOfByte[8];
                byte b3 = paramArrayOfByte[9];
                byte b4 = paramArrayOfByte[12];
                ACUtil aCUtil = ACUtil.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("fan-speed, left-temp, right-temp, in-temp: ");
                stringBuilder.append(Integer.toBinaryString(b1));
                stringBuilder.append("; ");
                stringBuilder.append(b2);
                stringBuilder.append("; ");
                stringBuilder.append(b3);
                stringBuilder.append("; ");
                stringBuilder.append(b4);
                Timber.e(stringBuilder.toString());
                aCUtil.setFanSpeed(b1);
                aCUtil.setLeftTemp(b2);
                aCUtil.setRightTemp(b3);
                aCUtil.setInTemp(b4);
                Timber.e("ac-mode: %s", String.valueOf(paramArrayOfByte[6]));
                aCUtil.setAcMode(paramArrayOfByte[6] & 0xFF);
            }
        } else {
            ACUtil aCUtil = null;
            if (paramArrayOfByte[3] == 13 && paramArrayOfByte[5] == 43) {
                byte b1 = paramArrayOfByte[7];
                byte b2 = paramArrayOfByte[8];
                aCUtil = ACUtil.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("seat-heat, seat-air: ");
                stringBuilder.append(Integer.toBinaryString(b1));
                stringBuilder.append("; ");
                stringBuilder.append(Integer.toBinaryString(b2));
                Timber.e(stringBuilder.toString());
                aCUtil.setSeatTemp(b1);
                aCUtil.setSeatAir(b2);
            } else {
                Timber.d("onSerialReceived: %s", StringUtil.fromByteArray(paramArrayOfByte));
            }
        }
    }
}