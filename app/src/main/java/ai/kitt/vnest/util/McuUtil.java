package ai.kitt.vnest.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;

import com.vnest.txznet.smartadapter.api.HandleCenter;

import ai.kitt.vnest.App;
import timber.log.Timber;

public class McuUtil {
    public static void airConditionerMode(int paramInt) {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, (byte)paramInt, b,
                b, b, b, b, b, b, b, b, b, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
        App.get().sendBroadcast(intent);
    }

    public static void anionMode(int paramInt) {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, b, b, b, b, b, b, b, b, b,
                -1, b, b, b, -1, -1, b, (byte)paramInt, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    private static void broadcast(String paramString, int paramInt) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("broadcast:");
        stringBuilder.append(paramString);
        stringBuilder.append(", ");
        stringBuilder.append(paramInt);
        Timber.d(stringBuilder.toString(), new Object[0]);
        try {
            Intent intent = new Intent();
//            this(paramString);
            intent.putExtra("commandCode", paramInt);
             App.get().sendBroadcast(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void checkTirePressure() { broadcast("com.jsbdtek.os.action", 75); }

    public static void checkVehicleInfo() { broadcast("com.jsbdtek.os.action", 72); }

    public static void defrostMode(int paramInt) {
        byte b1 = (byte)255;
        byte b2 = (byte)paramInt;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b1, b1, b1,
                b1, b2, b2, b1, b1, b1, b1, b1, b1, b1,
                -1, b1, b1, b1, -1, -1, b1, b1, -1, b1,
                b1, -1 }));
         App.get().sendBroadcast(intent);
    }

    private static void dualDefrost(int paramInt) {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, b, b, b, b, b, b, b, (byte)paramInt, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    private static void ffDefrost(int paramInt) {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, b, b, b, b, b, b, (byte)paramInt, b, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void frontDefrostMode(int paramInt) {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, (byte)paramInt, b, b, b, b, b, b, b, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void frontWindMode() {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, b, b, b, b, b, b, (byte)6, b, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void getAirConditionerStatus(Context paramContext) { paramContext.sendBroadcast(getCommandIntent(getCommand(3, new byte[] { 6, 0, 86 }))); }

    public static void getAirQualityInformation() { broadcast("com.jsbdtek.os.action", 79); }

    public static byte[] getCommand(int paramInt, byte... paramVarArgs) {
        byte[] arrayOfByte = new byte[paramInt + 4];
        byte b = 0;
        arrayOfByte[0] = (byte)-1;
        arrayOfByte[1] = (byte)102;
        arrayOfByte[2] = (byte)(byte)paramInt;
        while (b < paramInt) {
            arrayOfByte[b + 3] = (byte)paramVarArgs[b];
            b++;
        }
        arrayOfByte[paramInt + 3] = getCommandChecksum(arrayOfByte);
        return arrayOfByte;
    }

    public static byte getCommandChecksum(byte[] paramArrayOfByte) {
        byte b = 0;
        byte b1 = b;
        while (b <= paramArrayOfByte[2]) {
            b1 = (byte)(paramArrayOfByte[b + 2] + b1);
            b++;
        }
        byte b2 = (byte)(b1 + 1);
        Timber.d("checksum: %s", new Object[] { Byte.valueOf(b2) });
        return b2;
    }

    public static Intent getCommandIntent(byte[] paramArrayOfByte) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = paramArrayOfByte.length;
        for (byte b = 0; b < i; b++) {
            stringBuilder.append(paramArrayOfByte[b]);
            stringBuilder.append(" ");
        }
        Timber.d("command: %s", new Object[] { stringBuilder.toString().trim() });
        Intent intent = new Intent("com.jsbd.serial.apptomcu");
        intent.putExtra("DataLen", paramArrayOfByte.length);
        intent.putExtra("Data", paramArrayOfByte);
        return intent;
    }

    public static void getSeatStatus(Context paramContext) { paramContext.sendBroadcast(getCommandIntent(getCommand(3, new byte[] { 13, 0, 43 }))); }

    public static void insideWindLoop() {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                (byte)2, b, b, b, b, b, b, b, b, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void lowFanSpeed() {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, b, b, b, b, b, (byte)2, b, b, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void lowWindSpeed() {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, b, b, b, b, b, b, b, b, b,
                -1, (byte)1, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void mute(Context paramContext) {
        if (ModelUtil.isZotye()) {
            paramContext.sendBroadcast(getCommandIntent(getCommand(5, new byte[] { 18, 0, 9, 1, 3 })));
        } else {
            AudioManager audioManager = (AudioManager)paramContext.getSystemService(Context.AUDIO_SERVICE);
            if (Build.VERSION.SDK_INT >= 23) {
                audioManager.adjustStreamVolume(5, -100, 1);
                audioManager.adjustStreamVolume(4, -100, 1);
                audioManager.adjustStreamVolume(3, -100, 1);
                audioManager.adjustStreamVolume(2, -100, 1);
                audioManager.adjustStreamVolume(1, -100, 1);
            } else {
                audioManager.setStreamMute(5, true);
                audioManager.setStreamMute(4, true);
                audioManager.setStreamMute(3, true);
                audioManager.setStreamMute(2, true);
                audioManager.setStreamMute(1, true);
            }
        }
    }

    public static boolean nextRadioChannel(Context paramContext) {
        paramContext.sendBroadcast(getCommandIntent(getCommand(5, new byte[] { 18, 0, 9, 1, 10 })));
        return true;
    }

    public static void nextSong() { broadcast("com.jsbdtek.media.action", 42); }

    public static void openCDPlayer() { broadcast("com.jsbdtek.media.action", 30); }

    public static void openMusic() {}

    public static void outsideWindLoop() {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                (byte)1, b, b, b, b, b, b, b, b, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void pauseMusic() {}

    public static boolean prevRadioChannel(Context paramContext) {
        paramContext.sendBroadcast(getCommandIntent(getCommand(5, new byte[] { 18, 0, 9, 1, 9 })));
        return true;
    }

    public static void prevSong() { broadcast("com.jsbdtek.media.action", 43); }

    public static void provinceForecastMode(int paramInt) {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, b, b, b, b, b, b, b, b, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                (byte)paramInt, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void rearDefrostMode(int paramInt) {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, b, (byte)paramInt, b, b, b, b, b, b, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void roofTopWindowClose(Context paramContext) { paramContext.sendBroadcast(getCommandIntent(getCommand(7, new byte[] { 18, 0, 9, 17, 23, 1, 3 }))); }

    public static void roofTopWindowOpen(Context paramContext) { paramContext.sendBroadcast(getCommandIntent(getCommand(7, new byte[] { 18, 0, 9, 17, 23, 1, 2 }))); }

    public static boolean scanRadioStation(Context paramContext) {
        paramContext.sendBroadcast(getCommandIntent(getCommand(5, new byte[] { 18, 0, 9, 2, 3 })));
        return true;
    }

    public static void setAC(int paramInt) {
        int i = paramInt;
        if (paramInt < 16)
            i = 16;
        paramInt = i;
        if (i > 36)
            paramInt = 36;
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, b, b, b, b, b, b, b, b, (byte)(paramInt * 2),
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void setDefrostMode(int paramInt) {
        byte b1 = (byte)255;
        byte b2 = (byte)paramInt;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b1, b1, b1,
                b1, b2, b2, b1, b1, b1, b1, b1, b1, b1,
                -1, b1, b1, b1, -1, -1, b1, b1, -1, b1,
                b1, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static boolean turnBluetoothOff() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter == null) ? false : (bluetoothAdapter.isEnabled() ? true : bluetoothAdapter.disable());
    }

    public static boolean turnBluetoothOn() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter == null) ? false : (bluetoothAdapter.isEnabled() ? true : bluetoothAdapter.enable());
    }

    public static void unmute(Context paramContext) {
        if (ModelUtil.isZotye()) {
            paramContext.sendBroadcast(getCommandIntent(getCommand(5, new byte[] { 18, 0, 9, 1, 6 })));
        } else {
            AudioManager audioManager = (AudioManager)paramContext.getSystemService(Context.AUDIO_SERVICE);
            if (Build.VERSION.SDK_INT >= 23) {
                audioManager.adjustStreamVolume(5, 100, 1);
                audioManager.adjustStreamVolume(4, 100, 1);
                audioManager.adjustStreamVolume(3, 100, 1);
                audioManager.adjustStreamVolume(2, 100, 1);
                audioManager.adjustStreamVolume(1, 100, 1);
            } else {
                audioManager.setStreamMute(5, false);
                audioManager.setStreamMute(4, false);
                audioManager.setStreamMute(3, false);
                audioManager.setStreamMute(2, false);
                audioManager.setStreamMute(1, false);
            }
        }
    }

    public static void upFanSpeed() {
        byte b = (byte)255;
        Intent intent = getCommandIntent(getCommand(32, new byte[] {
                18, 0, 9, 17, 20, 1, 1, b, b, b,
                b, b, b, b, b, b, (byte)1, b, b, b,
                -1, b, b, b, -1, -1, b, b, -1, b,
                b, -1 }));
         App.get().sendBroadcast(intent);
    }

    public static void volumeDown(Context paramContext) {
        if (ModelUtil.isZotye()) {
            paramContext.sendBroadcast(getCommandIntent(getCommand(5, new byte[] { 18, 0, 9, 1, 2 })));
        } else if (ModelUtil.isJoying()) {
            HandleCenter.volumeDown();
        } else {
            AudioManager audioManager = (AudioManager) App.get().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                int i = audioManager.getStreamVolume(3);
                if (i > 1) {
                    i -= 2;
                } else {
                    i = 0;
                }
                audioManager.setStreamVolume(3, i, 1);
            }
        }
    }

    public static void volumeMax() {
        if (ModelUtil.isJoying())
            for (byte b = 0; b < 12; b++)
                HandleCenter.volumeUp();
    }

    public static void volumeMin() {
        if (ModelUtil.isJoying())
            for (byte b = 0; b < 12; b++)
                HandleCenter.volumeUp();
    }

    public static void volumeUp(Context paramContext) {
        if (ModelUtil.isZotye()) {
            paramContext.sendBroadcast(getCommandIntent(getCommand(5, new byte[] { 18, 0, 9, 1, 1 })));
        } else if (ModelUtil.isJoying()) {
            HandleCenter.volumeUp();
        } else {
            AudioManager audioManager = (AudioManager) App.get().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                int i = audioManager.getStreamMaxVolume(3);
                int j = audioManager.getStreamVolume(3);
                if (j < i - 1) {
                    if (j < i * 2 / 3) {
                        i = j + 2;
                    } else {
                        i = j + 1;
                    }
                } else if (j >= i) {
                    i = j;
                }
                audioManager.setStreamVolume(3, i, 1);
            }
        }
    }
}
