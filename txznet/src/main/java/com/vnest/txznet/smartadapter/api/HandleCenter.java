package com.vnest.txznet.smartadapter.api;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.exoplayer2.C;
//import com.syu.air.AirControl;
//import com.syu.sha.ipc.ITransitService;
//import com.syu.sha.ipc.OnBluetoothPhoneListener;
//import com.syu.sha.ipc.OnBluetoothPhoneListener.Stub;
//import com.syu.sha.ipc.OnDefaultNaviListener;
//import com.syu.sha.ipc.OnMachineStateListener;
import com.vnest.txznet.smartadapter.Params;

import kun.kt.opencam.air.AirControl;
import kun.kt.opencam.ipc.ITransitService;
import kun.kt.opencam.ipc.OnBluetoothPhoneListener;
import kun.kt.opencam.ipc.OnDefaultNaviListener;
import kun.kt.opencam.ipc.OnMachineStateListener;

public class HandleCenter {
    private static final String SHA_PKG = "com.syu.sha";
    private static final String SHA_SEV = "com.syu.sha.TransitService";
    private static final String TAG = "HandleCenter";
    private static Application app = null;
    /* access modifiers changed from: private */
    public static OnBluetoothPhoneListener bluetoothPhoneListener = new OnBluetoothPhoneListener.Stub() {
        public void onBluetoothState(int i, String str) {
            StringBuilder sb = new StringBuilder();
            sb.append("OnBluetoothPhoneListener: state=");
            sb.append(i);
            sb.append(", number=");
            sb.append(str);
            Log.d(HandleCenter.TAG, sb.toString());
            if (i == 0) {
                Params.BT_STATUS = 0;
            } else if (i == 2) {
                Params.BT_STATUS = 2;
            } else if (i == 3) {
                Params.BT_STATUS = 3;
            } else if (i == 4) {
                Params.BT_STATUS = 4;
            } else if (i == 5) {
                Params.BT_STATUS = 5;
            }
        }
    };
    private static ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(HandleCenter.TAG, "Connect to com.syu.sha");
            HandleCenter.ipcService = ITransitService.Stub.asInterface(iBinder);
            AirControl.initialize(HandleCenter.ipcService);
            try {
                HandleCenter.ipcService.addOnDefaultNaviListener(HandleCenter.naviListener);
                HandleCenter.ipcService.setOnMachineStateListener(HandleCenter.machineStateListener);
                HandleCenter.ipcService.setOnBluetoothPhoneListener(HandleCenter.bluetoothPhoneListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            HandleCenter.ipcService = null;
            HandleCenter.bindShaService();
        }
    };
    /* access modifiers changed from: private */
    public static ITransitService ipcService = null;
    /* access modifiers changed from: private */
    public static OnMachineStateListener machineStateListener = new OnMachineStateListener.Stub() {
        public void onStateChanged(int i, int i2) {
            StringBuilder sb = new StringBuilder();
            sb.append("onStateChanged: ");
            sb.append(i);
            sb.append(", ");
            sb.append(i2);
            Log.e(HandleCenter.TAG, sb.toString());
        }

        public void onHasCarRadio(int i) {
            StringBuilder sb = new StringBuilder();
            sb.append("onHasCarRadio: ");
            sb.append(i);
            Log.d(HandleCenter.TAG, sb.toString());
        }

        public void onBlackScreen(int i) {
            StringBuilder sb = new StringBuilder();
            sb.append("onBlackScreen: ");
            sb.append(i);
            Log.d(HandleCenter.TAG, sb.toString());
        }

        public void onReversing(int i) {
            StringBuilder sb = new StringBuilder();
            sb.append("onReversing: ");
            sb.append(i);
            Log.d(HandleCenter.TAG, sb.toString());
        }
    };
    /* access modifiers changed from: private */
    public static OnDefaultNaviListener naviListener = new OnDefaultNaviListener.Stub() {
        public void onDefaultNaviChange(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append("onDefaultNaviChange: ");
            sb.append(str);
            Log.d(HandleCenter.TAG, sb.toString());
            HandleCenter.navipkg = str;
        }
    };
    /* access modifiers changed from: private */
    public static String navipkg = "com.google.android.apps.maps";

    public static void backHome() {
    }

    public static void setApp(Application application) {
        app = application;
    }

    @SuppressLint("WrongConstant")
    public static void bindShaService() {
        Intent intent = new Intent(SHA_SEV);
        intent.setPackage(SHA_PKG);
        app.bindService(intent, connection, 1);
    }

    public static String getUuid() {
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class}).invoke(cls, new Object[]{"ro.serialno"});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint("WrongConstant")
    public static void startApp(String str) {
        Application application = app;
        if (application != null) {
            try {
                Intent launchIntentForPackage = application.getPackageManager().getLaunchIntentForPackage(str);
                if (launchIntentForPackage != null) {
                    launchIntentForPackage.setFlags(C.ENCODING_PCM_MU_LAW);
                    app.startActivity(launchIntentForPackage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("WrongConstant")
    public static void startApp(String str, String str2) {
        if (app != null) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(str, str2));
                intent.setFlags(C.ENCODING_PCM_MU_LAW);
                app.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeApp(String str) {
        if (isServiceConnected()) {
            String str2 = TAG;
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("kill ");
                sb.append(str);
                Log.d(str2, sb.toString());
                ipcService.closeApp(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void startNavi() {
        startApp(navipkg);
    }

    public static void closeNavi() {
        closeApp(navipkg);
    }

    public static void play() {
        if (isServiceConnected()) {
            try {
                ipcService.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void pause() {
        if (isServiceConnected()) {
            try {
                ipcService.pause();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void prev() {
        if (isServiceConnected()) {
            try {
                ipcService.prev();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void next() {
        if (isServiceConnected()) {
            try {
                ipcService.next();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void volumeUp() {
        if (isServiceConnected()) {
            try {
                ipcService.volumeUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void volumeDown() {
        if (isServiceConnected()) {
            try {
                ipcService.volumeDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void screenOn() {
        if (isServiceConnected()) {
            try {
                ipcService.sendCmd(0, 16, new int[]{0});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void screenOff() {
        if (isServiceConnected()) {
            try {
                ipcService.sendCmd(0, 16, new int[]{1});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void answer() {
        if (isServiceConnected()) {
            try {
                ipcService.answer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void hangup() {
        if (isServiceConnected()) {
            try {
                ipcService.hang();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void dial(String str) {
        if (isServiceConnected()) {
            try {
                ipcService.dial(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void redial() {
        if (isServiceConnected()) {
            try {
                ipcService.sendCmd(2, 8, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void mute(boolean z) {
        if (isServiceConnected()) {
            try {
                ITransitService iTransitService = ipcService;
                int[] iArr = new int[1];
                iArr[0] = z ? -3 : -4;
                iTransitService.sendCmd(4, 0, iArr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void actionMuteOther(boolean z) {
        if (isServiceConnected()) {
            if (z) {
                try {
                    ipcService.requestAudio();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ipcService.releaseAudio();
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static void setFM(float f) {
        if (isServiceConnected()) {
            try {
                ipcService.setRadioFreq(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void radioSearchUp() {
        if (isServiceConnected()) {
            try {
                ipcService.sendCmd(0, 0, new int[]{1});
                ipcService.sendCmd(1, 5, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void radioSearchDown() {
        if (isServiceConnected()) {
            try {
                ipcService.sendCmd(0, 0, new int[]{1});
                ipcService.sendCmd(1, 6, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void brightnessMax() {
        if (isServiceConnected()) {
            try {
                ipcService.sendCmd(0, 10, new int[]{100});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void brightnessMin() {
        if (isServiceConnected()) {
            try {
                ipcService.sendCmd(0, 10, new int[]{0});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void brightnessUp() {
        if (isServiceConnected()) {
            try {
                ipcService.sendCmd(0, 10, new int[]{-4});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void brightnessDown() {
        if (isServiceConnected()) {
            try {
                ipcService.sendCmd(0, 10, new int[]{-5});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendCmd(int... iArr) {
        if (isServiceConnected()) {
            try {
                ipcService.sendAirCmd(20, iArr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isServiceConnected() {
        if (ipcService != null) {
            return true;
        }
        bindShaService();
        return false;
    }
}
