package com.vnest.txznet.smartadapter;

import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class Params {
    public static final String ACTION_NOTIFY_CARPLAY = "com.zjinnova.zlink";
    public static final String ACTION_RECV = "txz.action.smartadapter.RECV";
    public static final String ACTION_SEND = "txz.action.smartadapter.SEND";
    public static final String ACTION_WAKEUP = "com.syu.ms";
    public static boolean AEC_COMPARE_CHANNEL = false;
    public static boolean AEC_ENABLE = true;
    public static int BT_STATUS = -1;
    public static boolean CMD_SAVE_TO_FILE = false;
    public static String CORE_SVN = "";
    public static String CORE_TIME = "";
    public static boolean DEBUG = true;
    public static final String EXTRA_NOTIFY_CARPLAY_COMMAND = "command";
    public static final String EXTRA_NOTIFY_CARPLAY_PHONETYPE = "phoneType";
    public static final String EXTRA_NOTIFY_CARPLAY_STATUS = "status";
    public static final String EXTRA_WAKEUP_CMDACC = "cmd.acc";
    public static boolean HIT_TTS_ENABLE = false;
    public static String LANGUAGE_KEY = "";
    public static String LANGUAGE_VALUE = "";
    public static ArrayList<String> LIST_PATH_CONFIG_FILE = null;
    public static final String TAG = "TXZSmart";
    public static boolean TOAST_ENABLE = true;
    public static boolean isActive = false;

    static {
        ArrayList<String> arrayList = new ArrayList<>();
        LIST_PATH_CONFIG_FILE = arrayList;
        arrayList.add("/system/etc/txz");
        LIST_PATH_CONFIG_FILE.add("/system/txz");
        LIST_PATH_CONFIG_FILE.add("/oem/txz");
        LIST_PATH_CONFIG_FILE.add("/oem/app");
        ArrayList<String> arrayList2 = LIST_PATH_CONFIG_FILE;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append("txz");
        arrayList2.add(sb.toString());
    }

    public static String getConfigFilePath() {
        int i = 0;
        while (i < LIST_PATH_CONFIG_FILE.size()) {
            String[] list = new File((String) LIST_PATH_CONFIG_FILE.get(i)).list(new FilenameFilter() {
                public boolean accept(File file, String str) {
                    return str.endsWith(".wakeup") || str.equals("txz.voice.cfg");
                }
            });
            if (list == null || list.length <= 0) {
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("wakeup file path: ");
                sb.append((String) LIST_PATH_CONFIG_FILE.get(i));
                return (String) LIST_PATH_CONFIG_FILE.get(i);
            }
        }
        return "";
    }
}
