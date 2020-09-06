package ai.kitt.vnest.util;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.kitt.vnest.App;
import ai.kitt.vnest.util.event.ResponseEvent;
import kun.kt.opencam.air.AirControl;
import timber.log.Timber;

@SuppressLint("TimberArgTypes")
public class ACUtil extends AbstractFeature implements AirConditionerImpl {
    private static ACUtil instance;

    private static Object lock = new Object();

    private int acMode = 255;

    private int inTemp;

    private boolean isUp = false;

    private int leftTemp;

    private int rightTemp;

    private int seatFanSpeed;

    private int seatTemp;

    private int speed = 255;

    private void broadcast(Intent paramIntent) {
        App.get().sendBroadcast(paramIntent);
    }

    public static ACUtil getInstance() {
        synchronized (lock) {
            if (instance == null) {
                ACUtil aCUtil = new ACUtil();
//                getInstance();
                instance = aCUtil;
            }
            return instance;
        }
    }

    public void down() {
        if (isDown()) {
            getInstance().turnOn();
            (new Handler()).postDelayed(() -> {
//                new _$$Lambda$ACUtil$Pin_3XfBDsOBtlggmNlDCliPtl8(this)
//                ACUtil.getInstance();
            }, 300L);
        } else {
            Command command = (new Command()).setTemperatureRequest(2);
            broadcast(getCommandIntent(getCommand(command.getSize(), command.build())));
        }
    }

    public int getFanSpeed() {
        return this.speed;
    }

    public int getInTemp() {
        return this.inTemp;
    }

    public int getLeftTemp() {
        return this.leftTemp;
    }

    public int getRightTemp() {
        return this.rightTemp;
    }

    public void getStatus() {
        broadcast(getCommandIntent(getCommand(3, new byte[]{6, 0, 86})));
        broadcast(getCommandIntent(getCommand(3, new byte[]{13, 0, 43})));
    }

    public boolean isDown() {
        boolean bool1;
        boolean bool = true;
        if (this.acMode == 128) {
            bool1 = true;
        } else {
            bool1 = false;
        }
        Timber.d("isDown %s", new Object[]{Boolean.valueOf(bool1)});
        if (this.acMode == 128) {
            bool1 = bool;
        } else {
            bool1 = false;
        }
        return bool1;
    }

    public boolean process(String paramString) {
        if (RegexUtil.contains(paramString, ai.kitt.vnest.util.common.Command.MODE_ON)) {
            turnOn();
            if (RegexUtil.contains(paramString, new String[]{"máy lạnh"})) {
                EventBus.getDefault().post(new ResponseEvent("Okay, đã bật máy lạnh", true));
            } else {
                EventBus.getDefault().post(new ResponseEvent("Okay, đã bật điều hòa", true));
            }
            onLogCommand(paramString, "air_conditioner");
            return true;
        }
        if (RegexUtil.contains(paramString, ai.kitt.vnest.util.common.Command.MODE_OFF)) {
            turnOff();
            if (RegexUtil.contains(paramString, new String[]{"máy lạnh"})) {
                EventBus.getDefault().post(new ResponseEvent("Okay, đã tắt máy lạnh", true));
            } else {
                EventBus.getDefault().post(new ResponseEvent("Okay, đã tắt điều hòa", true));
            }
            onLogCommand(paramString, "air_conditioner");
            return true;
        }
        if (RegexUtil.contains(paramString, ai.kitt.vnest.util.common.Command.MODE_CHANGE) && RegexUtil.contains(paramString, new String[]{"độ", "độ c", "độ C", "Độ", "Độ c", "Độ C"})) {
            int b;
            Matcher matcher;
            boolean bool = true;
            try {
                matcher = Pattern.compile("\\d+").matcher(paramString);
                int i = 0;
                while (true) {
                    b = i;
                    try {
                        if (matcher.find()) {
                            b = Integer.parseInt(matcher.group());
                            i = b;
                            continue;
                        }
                        break;
                    } catch (Exception matcherException) {
                        // Byte code: goto -> 244
                    }
                }
            } catch (Exception exception) {
                bool = false;
                exception.printStackTrace();
                b = 0;
            }
            if (b!=0) {
                getInstance().setTemperature(b);
                if (RegexUtil.contains(paramString, new String[]{"tăng"})) {
//                    EventBus.getDefault().post(new ResponseEvent(StringUtils.getString(2131755052, new Object[]{Integer.valueOf(b)}), true));
                } else if (RegexUtil.contains(paramString, new String[]{"giảm"})) {
//                    EventBus.getDefault().post(new ResponseEvent(StringUtils.getString(2131755051, new Object[]{Integer.valueOf(b)}), true));
                } else {
//                    EventBus.getDefault().post(new ResponseEvent(StringUtils.getString(2131755050, new Object[]{Integer.valueOf(b)}), true));
                }
                onLogCommand(paramString, "air_conditioner");
            } else if (RegexUtil.contains(paramString, new String[]{"tăng"})) {
                getInstance().up();
                EventBus.getDefault().post(new ResponseEvent("Okay, đã tăng nhiệt độ", true));
                onLogCommand(paramString, "air_conditioner");
            } else if (RegexUtil.contains(paramString, new String[]{"giảm"})) {
                getInstance().down();
                EventBus.getDefault().post(new ResponseEvent("Okay, đã giảm nhiệt độ", true));
                onLogCommand(paramString, "air_conditioner");
            } else {
                EventBus.getDefault().post(new ResponseEvent("Không xác định được nhiệt độ bạn muốn điều chỉnh", true));
            }
        } else if (RegexUtil.equalsIgnoreCase(paramString, ai.kitt.vnest.util.common.Command.AC_AUTO_ON)) {
            Timber.d("Feature -> Bật điều hòa tự động");
            getInstance().setAuto(true);
            EventBus.getDefault().post(new ResponseEvent("Okay, đã kích hoạt chế độ điều hòa tự động", true));
            onLogCommand(paramString, "air_conditioner");
        } else if (RegexUtil.equalsIgnoreCase(paramString, ai.kitt.vnest.util.common.Command.AC_AUTO_OFF)) {
            getInstance().setAuto(false);
            EventBus.getDefault().post(new ResponseEvent("Okay, đã tắt chế độ điều hòa tự động", true));
            onLogCommand(paramString, "air_conditioner");
        } else if (RegexUtil.equalsIgnoreCase(paramString, ai.kitt.vnest.util.common.Command.AC_DUAL_MODE)) {
            Timber.d("Feature -> Điều hòa hai vùng");
            getInstance().setDual(true);
            EventBus.getDefault().post(new ResponseEvent("Okay, đã kích hoạt chế độ điều hòa hai vùng", true));
            onLogCommand(paramString, "air_conditioner");
        } else if (RegexUtil.equalsIgnoreCase(paramString, ai.kitt.vnest.util.common.Command.AC_SINGLE_MODE)) {
            Timber.d("Feature -> Điều hòa vùng độc lập");
            getInstance().setDual(false);
            EventBus.getDefault().post(new ResponseEvent("Okay, đã tắt chế độ điều hòa hai vùng", true));
            onLogCommand(paramString, "air_conditioner");
        }
        return false;
    }

    public void setAcMode(int paramInt) {
        Timber.e("setAcMode: %d", new Object[]{Integer.valueOf(paramInt)});
        this.acMode = paramInt;
    }

    public void setAnion(boolean paramBoolean) {
        if (isDown()) {
            turnOn();
            (new Handler()).postDelayed(() -> ACUtil.getInstance().setAnion(paramBoolean), 300L);
        } else {
            byte b;
            Command command = new Command();
            if (paramBoolean) {
                b = 2;
            } else {
                b = 1;
            }
            command = command.setAnion(b);
            broadcast(getCommandIntent(getCommand(command.getSize(), command.build())));
        }
    }

    public void setAuto(boolean paramBoolean) {
        if (isDown()) {
            turnOn();
            (new Handler()).postDelayed(() -> ACUtil.getInstance().setAuto(paramBoolean), 300L);
        } else {
            byte b;
            Command command = new Command();
            if (paramBoolean) {
                b = 2;
            } else {
                b = 1;
            }
            command = command.setAutoStatus(b);
            broadcast(getCommandIntent(getCommand(command.getSize(), command.build())));
        }
    }

    public void setDual(boolean paramBoolean) {
        if (isDown()) {
            turnOn();
            (new Handler()).postDelayed(() -> {ACUtil.getInstance().setDual(paramBoolean);
            }, 300L);
        } else {
            byte b;
            Command command = new Command();
            if (paramBoolean) {
                b = 1;
            } else {
                b = 2;
            }
            command = command.setDualStatus(b);
            broadcast(getCommandIntent(getCommand(command.getSize(), command.build())));
        }
    }

    public void setFanSpeed(int paramInt) {
        Timber.e("setFanSpeed: %d", Integer.valueOf(paramInt));
        this.speed = paramInt;
    }

    public void setInTemp(int paramInt) {
        Timber.d("setInTemp: %d", Integer.valueOf(paramInt));
        this.rightTemp = paramInt;
        this.leftTemp = paramInt;
        this.inTemp = paramInt;
    }

    public void setLeftTemp(int paramInt) {
        Timber.d("setLeftTemp: %d", Integer.valueOf(paramInt));
        this.leftTemp = paramInt;
    }

    public void setRightTemp(int paramInt) {
        Timber.d("setRightTemp: %d", new Object[]{Integer.valueOf(paramInt)});
        this.rightTemp = paramInt;
    }

    public void setSeatAir(int paramInt) {
        Timber.d("setSeatAir: %d", new Object[]{Integer.valueOf(paramInt)});
        this.seatFanSpeed = paramInt;
    }

    public void setSeatTemp(int paramInt) {
        Timber.d("setSeatTemp: %d", new Object[]{Integer.valueOf(paramInt)});
        this.seatTemp = paramInt;
    }

    public int getSeatFanSpeed() {
        return seatFanSpeed;
    }

    public void setSeatFanSpeed(int seatFanSpeed) {
        this.seatFanSpeed = seatFanSpeed;
    }

    public int getSeatTemp() {
        return seatTemp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setTemperature(int paramInt) {
        int i = paramInt;
        if (paramInt < 16)
            i = 16;
        paramInt = i;
        if (i > 30)
            paramInt = 30;
        if (ModelUtil.isZotye()) {
            if (isDown()) {
                turnOn();
                int finalParamInt = paramInt;
                (new Handler()).postDelayed(() -> ACUtil.getInstance().setTemperature(finalParamInt), 300L);
            } else {
                Command command = (new Command()).setDataTypeNum(paramInt * 2);
                broadcast(getCommandIntent(getCommand(command.getSize(), command.build())));
            }
        } else {
            AirControl.getInstance().setAirTempValue(4098, paramInt);
        }
    }

    public void turnOff() {
        if (ModelUtil.isZotye()) {
            Command command = (new Command()).setAirCondition(1);
            broadcast(getCommandIntent(getCommand(command.getSize(), command.build())));
        } else {
            Intent intent = new Intent("com.syu.Turkey_Va");
            intent.putExtra("ints", new int[]{33, 1});
            App.get().sendBroadcast(intent);
        }
    }

    public void turnOn() {
        if (ModelUtil.isZotye()) {
            Command command = (new Command()).setAirCondition(2);
            broadcast(getCommandIntent(getCommand(command.getSize(), command.build())));
        } else {
            Intent intent = new Intent("com.syu.Turkey_Va");
            intent.putExtra("ints", new int[]{33, 1});
            App.get().sendBroadcast(intent);
        }
    }

    @Override
    public void mo12646up() {

    }

    public void up() {
        if (isDown()) {
            getInstance().turnOn();
            (new Handler()).postDelayed(() -> ACUtil.getInstance().up(), 300L);
        } else {
            Command command = (new Command()).setTemperatureRequest(1);
            broadcast(getCommandIntent(getCommand(command.getSize(), command.build())));
        }
    }

    private static class Command {
        private final int COM_LENGTH = 32;

        int acMax = 255;

        int acSwitchRequest = 255;

        int airCondition = 255;

        int anion = 255;

        int autoCycle = 255;

        int autoStatus = 255;

        int cycleStatus = 255;

        int dataTypeNum = 255;

        int driverSeat = 255;

        int dualStatus = 255;

        int fanSpeedLevel = 255;

        int frontDefrost = 255;

        int frontHeat = 255;

        int hotMax = 255;

        int hvacModel = 255;

        int rearDefrost = 255;

        int tempElectricAirCondition = 255;

        int temperatureProvince = 255;

        int temperatureRequest = 255;

        int windSpeedRequest = 255;

        private Command() {
        }

        public byte[] build() {
            return new byte[]{
                    18, 0, 9, 17, 20, 1, 1, (byte) this.acSwitchRequest, (byte) this.airCondition, (byte) this.autoStatus,
                    (byte) this.cycleStatus, (byte) this.frontDefrost, (byte) this.rearDefrost, (byte) this.acMax, (byte) this.hotMax, (byte) this.temperatureRequest, (byte) this.windSpeedRequest, (byte) this.hvacModel, (byte) this.dualStatus, (byte) this.dataTypeNum,
                    -1, (byte) this.fanSpeedLevel, (byte) this.driverSeat, (byte) this.tempElectricAirCondition, -1, -1, (byte) this.autoCycle, (byte) this.anion, -1, (byte) this.frontHeat,
                    (byte) this.temperatureProvince, -1};
        }

        public int getAcMax() {
            return this.acMax;
        }

        public int getAcSwitchRequest() {
            return this.acSwitchRequest;
        }

        public int getAnion() {
            return this.anion;
        }

        public int getAutoCycle() {
            return this.autoCycle;
        }

        public int getAutoStatus() {
            return this.autoStatus;
        }

        public int getCycleStatus() {
            return this.cycleStatus;
        }

        public int getDataTypeNum() {
            return this.dataTypeNum;
        }

        public int getDriverSeat() {
            return this.driverSeat;
        }

        public int getDualStatus() {
            return this.dualStatus;
        }

        public int getFanSpeedLevel() {
            return this.fanSpeedLevel;
        }

        public int getFrontDefrost() {
            return this.frontDefrost;
        }

        public int getFrontHeat() {
            return this.frontHeat;
        }

        public int getHotMax() {
            return this.hotMax;
        }

        public int getHvacModel() {
            return this.hvacModel;
        }

        public int getRearDefrost() {
            return this.rearDefrost;
        }

        public int getSize() {
            return 32;
        }

        public int getTempElectricAirCondition() {
            return this.tempElectricAirCondition;
        }

        public int getTemperatureProvince() {
            return this.temperatureProvince;
        }

        public int getTemperatureRequest() {
            return this.temperatureRequest;
        }

        public int getWindSpeedRequest() {
            return this.windSpeedRequest;
        }

        public void setAcMax(int param1Int) {
            this.acMax = param1Int;
        }

        Command setAcSwitchRequest(int param1Int) {
            this.acSwitchRequest = param1Int;
            return this;
        }

        Command setAirCondition(int param1Int) {
            this.airCondition = param1Int;
            return this;
        }

        Command setAnion(int param1Int) {
            this.anion = param1Int;
            return this;
        }

        public void setAutoCycle(int param1Int) {
            this.autoCycle = param1Int;
        }

        Command setAutoStatus(int param1Int) {
            this.autoStatus = param1Int;
            return this;
        }

        public void setCycleStatus(int param1Int) {
            this.cycleStatus = param1Int;
        }

        Command setDataTypeNum(int param1Int) {
            this.dataTypeNum = param1Int;
            return this;
        }

        public void setDriverSeat(int param1Int) {
            this.driverSeat = param1Int;
        }

        Command setDualStatus(int param1Int) {
            this.dualStatus = param1Int;
            return this;
        }

        public void setFanSpeedLevel(int param1Int) {
            this.fanSpeedLevel = param1Int;
        }

        public void setFrontDefrost(int param1Int) {
            this.frontDefrost = param1Int;
        }

        public void setFrontHeat(int param1Int) {
            this.frontHeat = param1Int;
        }

        public void setHotMax(int param1Int) {
            this.hotMax = param1Int;
        }

        public void setHvacModel(int param1Int) {
            this.hvacModel = param1Int;
        }

        public void setRearDefrost(int param1Int) {
            this.rearDefrost = param1Int;
        }

        public void setTempElectricAirCondition(int param1Int) {
            this.tempElectricAirCondition = param1Int;
        }

        public void setTemperatureProvince(int param1Int) {
            this.temperatureProvince = param1Int;
        }

        Command setTemperatureRequest(int param1Int) {
            this.temperatureRequest = param1Int;
            return this;
        }

        public void setWindSpeedRequest(int param1Int) {
            this.windSpeedRequest = param1Int;
        }
    }
}