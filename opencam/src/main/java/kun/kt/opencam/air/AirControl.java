/**
 * 工程名:VoiceSettings
 * 文件名:AirControl.java
 * 包   名:com.syu.air
 * 日   期:2016年3月25日下午1:51:19
 * 作   者:fyt
 * Copyright (c) 2016, kexuan52@yeah.net All Rights Reserved.
 */
package kun.kt.opencam.air;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.SparseArray;

import kun.kt.opencam.ipc.ITransitService;


/**
 *类   名:AirControl
 *功   能:TODO
 *
 *日  期:2016年3月25日 下午1:51:19
 * @author fyt
 *
 */
public class AirControl {
    final public static int CAR_FRONT = 0x1000;
    final public static int CAR_REAR = 0x1001;
    final public static int CAR_LEFT_OR_ALL = 0x1002;
    final public static int CAR_RIGHT = 0x1003;
    public static final SparseArray<String> numNames = new SparseArray<>();

    static {
        //numNames = new SparseIntArray();
        numNames.put(16, "十六");
        numNames.put(17, "十七");
        numNames.put(18, "十八");
        numNames.put(19, "十九");
        numNames.put(20, "二十");
        numNames.put(21, "二十一");
        numNames.put(22, "二十二");
        numNames.put(23, "二十三");
        numNames.put(24, "二十四");
        numNames.put(25, "二十五");
        numNames.put(26, "二十六");
        numNames.put(27, "二十七");
        numNames.put(28, "二十八");
        numNames.put(29, "二十九");
        numNames.put(30, "三十");
        numNames.put(31, "三十一");
        numNames.put(32, "三十二");
    }

    //ACTION
    final public static int ACTION_UP = 1;
    final public static int ACTION_DOWN = -1;

    ITransitService mService;
    Handler mHandler;
    static AirControl instance;

    public static AirControl initialize(ITransitService service) {
        if (service != null && instance == null) {
            instance = new AirControl(service);
        }
        return instance;
    }

    public static void release() {
        instance = null;
    }

    /**
     * @return the instance
     */
    public static AirControl getInstance() {
        return instance;
    }

    AirControl(ITransitService service) {
        mService = service;
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * AC开关
     */
    public void acSwitch(boolean on) {
        sendCmd(FinalAir.VA_CMD_AIR_AC_ON, on ? 1 : 0);
    }

    /**
     * 空调总开关
     */
    public void airPower(boolean on) {
        sendCmd(FinalAir.VA_CMD_AIR_POWER_ON, on ? 1 : 0);
    }

    public void autoSwitch(boolean on) {
        sendCmd(FinalAir.VA_CMD_AIR_AUTO_ON, on ? 1 : 0);
    }

    public void frontDefogging(boolean on) {
        sendCmd(FinalAir.VA_CMD_AIR_FRONT_DEFROST_ON, on ? 1 : 0);
    }

    public void rearDefogging(boolean on) {
        sendCmd(FinalAir.VA_CMD_AIR_REAR_DEFROST_ON, on ? 1 : 0);
    }

    public void controlTrunk(boolean on) {
        sendCmd(FinalAir.VA_TRUNK_CONTROL_CMD, on ? 1 : 0);
    }

    public void setAirVolume(int area, int action, int vol) {
        int cmdid = -1;
        int param = -1;
        if (area == CAR_LEFT_OR_ALL) {
            cmdid = FinalAir.VA_CMD_AIR_BLOW_LEVEL;
        } else if (area == CAR_RIGHT) {
            cmdid = FinalAir.VA_CMD_AIR_BLOW_LEVEL_RIGHT;
        }

        if (action == ACTION_UP) {
            param = -1;
        } else if (action == ACTION_DOWN) {
            param = -2;
        }

        if (cmdid > 0 && param < 0) {
            sendCmd(cmdid, param);
        }
    }

    public void setAirTemp(int area, int action, int temp) {
        int cmdid = -1;
        int param = -1;
        if (area == CAR_LEFT_OR_ALL) {
            cmdid = FinalAir.VA_CMD_AIR_TEMP_LEFT;
        } else if (area == CAR_RIGHT) {
            cmdid = FinalAir.VA_CMD_AIR_TEMP_RIGHT;
        }

        if (action == ACTION_UP) {
            param = 1;
        } else if (action == ACTION_DOWN) {
            param = 0;
        }

        if (cmdid > 0 && param >= 0) {
            sendCmd(cmdid, param);
        }
    }

    public void setAirTempValue(int area, int temp) {
        int cmdid = -1;
        if (area == CAR_LEFT_OR_ALL) {
            cmdid = FinalAir.VA_CMD_AIR_TEMP_LEFT_DIRECT;
        } else if (area == CAR_RIGHT) {
            cmdid = FinalAir.VA_CMD_AIR_TEMP_RIGHT_DIRECT;
        }

        if (cmdid > 0 && temp >= 0) {
            sendCmd(cmdid, temp);
        }
    }

    public void setAirCylce(int mode) {
        sendCmd(FinalAir.VA_CMD_AIR_CYCLE_TYPE, mode);
    }

    public void setEngineMode(int mode) {
        sendCmd(FinalAir.VA_CMD_DJ_PENGPAI, mode);
    }

    void sendCmd(int... params) {
        if (params == null) return;
        if (mService != null) {
            try {
                mService.sendAirCmd(20, params);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
