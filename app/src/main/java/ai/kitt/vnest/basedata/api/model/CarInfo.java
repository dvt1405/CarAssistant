package ai.kitt.vnest.basedata.api.model;

import android.os.Build;

import ai.kitt.vnest.BuildConfig;


public class CarInfo {
    public CarInfo(String deviceId) {
        this.deviceId = deviceId;
    }

    public CarInfo(String deviceId, String model, String id, String brand, String type, String user,
                   String base, String sdk, String incremental, String board, String host, String versionCode, String fingerPrint, String versionName, String imei) {
        this.deviceId = deviceId;
        this.model = model;
        this.id = id;
        this.brand = brand;
        this.type = type;
        this.user = user;
        this.base = base;
        this.sdk = sdk;
        this.incremental = incremental;
        this.board = board;
        this.host = host;
        this.versionCode = versionCode;
        this.fingerPrint = fingerPrint;
        this.versionName = versionName;
        this.imei = imei;
    }

    public String deviceId;
    public String model;
    public String id;
    public String brand;
    public String type;
    public String user;
    public String base;
    public String sdk;
    public String incremental;
    public String board;
    public String host;
    public String versionCode;
    public String fingerPrint;
    private String versionName;
    private String imei;

    public static CarInfo getDefault(String deviceId, String imei) {
        return new CarInfo(
                deviceId,
                Build.MODEL,
                Build.ID,
                Build.BRAND,
                Build.TYPE,
                Build.USER,
                Build.VERSION_CODES.BASE + "",
                Build.VERSION.SDK_INT + "",
                Build.VERSION.INCREMENTAL,
                Build.BOARD,
                Build.HOST,
                BuildConfig.VERSION_CODE + "",
                Build.FINGERPRINT,
                BuildConfig.VERSION_NAME,
                imei
        );
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "deviceId='" + deviceId + '\'' +
                ", model='" + model + '\'' +
                ", id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", user='" + user + '\'' +
                ", base='" + base + '\'' +
                ", sdk='" + sdk + '\'' +
                ", incremental='" + incremental + '\'' +
                ", board='" + board + '\'' +
                ", host='" + host + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", fingerPrint='" + fingerPrint + '\'' +
                '}';
    }
}
