package com.vnest.txznet.sdk;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseWakeupManager {
    public static final int ERROR_INITIALIZED = 1;
    public static final int ERROR_SUCCESS = 0;
    protected static final int STATE_EXIT = 3;
    protected static final int STATE_INITIALIZED = 1;
    protected static final int STATE_START = 4;
    protected static final int STATE_STOP = 5;
    protected static final int STATE_UNINITIALIZED = 2;
    protected static final String TAG = "TXZ";
    private Boolean bFloatViewShow = null;
    private boolean isUpdateKeywords = false;
    private ArrayList<String> mConfigPathList = null;
    protected Context mContext;
    protected OnEventListener mEventListener;
    protected InitParam mInitParam;
    protected boolean mInitedSuccess = false;
    private String[] mKeywords = null;
    protected OnWakeupListener mListener;
    private Integer mLogLevel = null;
    protected int mState = 2;
    private Integer mWakeupThreshold = null;

    public static class InitParam {
        private String appToken = null;
        private String appid = null;
        private boolean compareWithRightChannel = true;
        public Boolean enableChangeLanguageWithSystem = null;
        private boolean enablePlayVoiceHit = true;
        public String language = null;
        private String[] mMainWakeupKeywords = null;
        private String[] mWakeupKeywords = null;
        private boolean needAEC = false;
        private HashMap<String, Float> thresholdKeyWords;
        private String uuid = null;
        private String wakeupKeywordsGrammaerPath = null;

        public boolean isEnablePlayVoiceHit() {
            return this.enablePlayVoiceHit;
        }

        public String getAppid() {
            return this.appid;
        }

        public String getAppToken() {
            return this.appToken;
        }

        public String getUuid() {
            return this.uuid;
        }

        public String[] getWakeupKeywords() {
            return this.mWakeupKeywords;
        }

        public String[] getMainWakeupKeywords() {
            return this.mMainWakeupKeywords;
        }

        public HashMap<String, Float> getThresholdKeyWords() {
            return this.thresholdKeyWords;
        }

        public String getWakeupKeywordsGrammaerPath() {
            return this.wakeupKeywordsGrammaerPath;
        }

        public boolean isNeedAEC() {
            return this.needAEC;
        }

        public boolean isCompareWithRightChannel() {
            return this.compareWithRightChannel;
        }

        public InitParam setAppid(String str) {
            this.appid = str;
            return this;
        }

        public InitParam setAppToken(String str) {
            this.appToken = str;
            return this;
        }

        public InitParam setUuid(String str) {
            this.uuid = str;
            return this;
        }

        public InitParam setWakupKeywords(String[] strArr) {
            this.mWakeupKeywords = strArr;
            return this;
        }

        public InitParam setMainWakupKeywords(String[] strArr) {
            this.mMainWakeupKeywords = strArr;
            return this;
        }

        public InitParam setEnableVoiceHit(boolean z) {
            this.enablePlayVoiceHit = z;
            return this;
        }

        public InitParam setWakeupKeywordsThreshold(HashMap<String, Float> hashMap) {
            this.thresholdKeyWords = hashMap;
            return this;
        }

        public InitParam setWakeupKeywordsGrammaerPath(String str) {
            this.wakeupKeywordsGrammaerPath = str;
            return this;
        }

        public InitParam setNeedAEC(boolean z) {
            this.needAEC = z;
            return this;
        }

        public InitParam setCompareWithRightChannel(boolean z) {
            this.compareWithRightChannel = z;
            return this;
        }

        public InitParam setLanguage(String str) {
            this.language = str;
            return this;
        }

        public InitParam enableChangeLanguageWithSystem(boolean z) {
            this.enableChangeLanguageWithSystem = Boolean.valueOf(z);
            return this;
        }

        public boolean checkParam() {
            if (!TextUtils.isEmpty(this.appid) && !TextUtils.isEmpty(this.appToken)) {
                return true;
            }
            throw new AndroidRuntimeException("appid or appToken is empty");
        }
    }

    public interface OnEventListener {
        void onEvent(int i, String str);
    }

    public interface OnWakeupListener {
        void onInit(boolean z);

        void onWakeUp(String str, String str2);
    }

    /* access modifiers changed from: protected */
    public abstract void initializeEnv();

    /* access modifiers changed from: protected */
    public abstract void sendBroadcast(Bundle bundle);

    public boolean isInitedSuccess() {
        return this.mInitedSuccess;
    }

    public int initialize(Context context, InitParam initParam, OnWakeupListener onWakeupListener) {
        if (context == null || initParam == null) {
            throw new AndroidRuntimeException("Context or InitParam == null");
        } else if (this.mContext == null || this.mInitParam == null) {
            this.mState = 1;
            this.mContext = context;
            this.mInitParam = initParam;
            this.mListener = onWakeupListener;
            initializeEnv();
            doInit();
            return 0;
        } else {
            Log.w(TAG, "already initialized...");
            return 1;
        }
    }

    public void doInit() {
        Bundle bundle = new Bundle();
        bundle.putInt("command", 1);
        InitParam initParam = this.mInitParam;
        if (initParam != null) {
            initParam.checkParam();
            bundle.putString(Constants.KEY_INIT_APPID, this.mInitParam.getAppid());
            bundle.putString(Constants.KEY_INIT_APPTOKEN, this.mInitParam.getAppToken());
            if (!TextUtils.isEmpty(this.mInitParam.getUuid())) {
                bundle.putString(Constants.KEY_INIT_UUID, this.mInitParam.getUuid());
            }
            if (this.mInitParam.getWakeupKeywords() != null) {
                bundle.putStringArray("keywords", this.mInitParam.getWakeupKeywords());
            }
            if (this.mInitParam.getMainWakeupKeywords() != null) {
                bundle.putStringArray(Constants.KEY_INIT_MAIN_KEYWORDS, this.mInitParam.getMainWakeupKeywords());
            }
            if (this.mInitParam.getThresholdKeyWords() != null) {
                JSONArray jSONArray = new JSONArray();
                for (Entry entry : this.mInitParam.getThresholdKeyWords().entrySet()) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("keyWords", entry.getKey());
                        jSONObject.put("threshold", entry.getValue());
                        jSONArray.put(jSONObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                bundle.putString(Constants.KEY_INIT_KWSTHRESHOLD, jSONArray.toString());
            }
            bundle.putBoolean(Constants.KEY_INIT_VOICE_HIT, this.mInitParam.isEnablePlayVoiceHit());
            bundle.putBoolean(Constants.KEY_INIT_NEED_AEC, this.mInitParam.isNeedAEC());
            bundle.putBoolean(Constants.KEY_INIT_COMPARE_RIGHT_CHANNEL, this.mInitParam.isCompareWithRightChannel());
            if (this.mInitParam.language != null) {
                bundle.putString(Constants.KEY_INIT_LANGUAGE, this.mInitParam.language);
            }
            if (this.mInitParam.enableChangeLanguageWithSystem != null) {
                bundle.putBoolean(Constants.KEY_INIT_ENABLE_LANGUAGE_CHANGE_WITH_SYSTEM, this.mInitParam.enableChangeLanguageWithSystem.booleanValue());
            }
            if (!TextUtils.isEmpty(this.mInitParam.getWakeupKeywordsGrammaerPath())) {
                bundle.putString(Constants.KEY_INIT_KEYWORD_GRAMMAR_PATH, this.mInitParam.getWakeupKeywordsGrammaerPath());
            }
            sendBroadcast(bundle);
            return;
        }
        throw new AndroidRuntimeException("InitParam == null");
    }

    public void reinit() {
        Log.d(TAG, "reinit... ");
        this.mState = 1;
        doInit();
    }

    public void exit() {
        Log.d(TAG, "kill txz process... ");
        this.mState = 3;
        Bundle bundle = new Bundle();
        bundle.putInt("command", 2);
        sendBroadcast(bundle);
        onInit(false);
    }

    public void startWakeup() {
        Log.d(TAG, "start wakup");
        this.mState = 4;
        Bundle bundle = new Bundle();
        bundle.putInt("command", 3);
        sendBroadcast(bundle);
    }

    public void stopWakeup() {
        Log.d(TAG, "stop wakup");
        this.mState = 5;
        Bundle bundle = new Bundle();
        bundle.putInt("command", 4);
        sendBroadcast(bundle);
    }

    public void updateWakeupKeywords(String[] strArr) {
        Log.d(TAG, "update keywords");
        this.mKeywords = strArr;
        this.isUpdateKeywords = true;
        Bundle bundle = new Bundle();
        bundle.putInt("command", 5);
        bundle.putStringArray("keywords", this.mKeywords);
        sendBroadcast(bundle);
    }

    public void regCommand(String[] strArr, String str) {
        Log.d(TAG, "regCommand");
        Bundle bundle = new Bundle();
        bundle.putInt("command", 7);
        bundle.putStringArray("cmds", strArr);
        bundle.putString("data", str);
        sendBroadcast(bundle);
    }

    public void unregCommand(String[] strArr) {
        Log.d(TAG, "unregCommand");
        Bundle bundle = new Bundle();
        bundle.putInt("command", 8);
        bundle.putStringArray("cmds", strArr);
        sendBroadcast(bundle);
    }

    public void setLogcatLevel(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("setLogcatLevel ");
        sb.append(i);
        Log.d(TAG, sb.toString());
        this.mLogLevel = Integer.valueOf(i);
        Bundle bundle = new Bundle();
        bundle.putInt("command", 9);
        bundle.putInt("level", i);
        sendBroadcast(bundle);
    }

    public void setWakeupThreshold(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("setWakeupThreshold = ");
        sb.append(i);
        Log.d(TAG, sb.toString());
        this.mWakeupThreshold = Integer.valueOf(i);
        Bundle bundle = new Bundle();
        bundle.putInt("command", 10);
        bundle.putInt("threshold", i);
        sendBroadcast(bundle);
    }

    public void addConfigPathList(ArrayList<String> arrayList) {
        Bundle bundle = new Bundle();
        bundle.putInt("command", 12);
        bundle.putStringArrayList("pathList", arrayList);
        sendBroadcast(bundle);
    }

    public void openFloatView() {
        this.bFloatViewShow = Boolean.valueOf(true);
        Bundle bundle = new Bundle();
        bundle.putInt("command", 13);
        bundle.putBoolean("show", true);
        sendBroadcast(bundle);
    }

    public void closeFloatView() {
        this.bFloatViewShow = Boolean.valueOf(false);
        Bundle bundle = new Bundle();
        bundle.putInt("command", 13);
        bundle.putBoolean("show", false);
        sendBroadcast(bundle);
    }

    public void getBuildInfo() {
        Bundle bundle = new Bundle();
        bundle.putInt("command", 14);
        sendBroadcast(bundle);
    }

    public void changeLanguage(String str) {
        this.mInitParam.language = str;
        Bundle bundle = new Bundle();
        bundle.putInt("command", 15);
        bundle.putString("vi", str);
        sendBroadcast(bundle);
    }

    public void onReconnectTXZ() {
        if (this.mInitedSuccess) {
            int i = this.mState;
            if (!(i == 3 || i == 2)) {
                doInit();
            }
            TxzAsrManager.getInstance().onReconnectTXZ();
        }
        Integer num = this.mLogLevel;
        if (num != null) {
            setLogcatLevel(num.intValue());
        }
        Integer num2 = this.mWakeupThreshold;
        if (num2 != null) {
            setWakeupThreshold(num2.intValue());
        }
        ArrayList<String> arrayList = this.mConfigPathList;
        if (arrayList != null) {
            addConfigPathList(arrayList);
        }
        Boolean bool = this.bFloatViewShow;
        if (bool != null) {
            if (bool.booleanValue()) {
                openFloatView();
            } else {
                closeFloatView();
            }
        }
    }

    private void onInit(boolean z) {
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append("check state: ");
            sb.append(this.mState);
            Log.d(TAG, sb.toString());
            if (this.isUpdateKeywords) {
                updateWakeupKeywords(this.mKeywords);
            }
            if (this.mState == 4) {
                startWakeup();
            }
        }
        if (this.mInitedSuccess != z) {
            this.mInitedSuccess = z;
            OnWakeupListener onWakeupListener = this.mListener;
            if (onWakeupListener != null) {
                onWakeupListener.onInit(z);
            }
        }
    }

    public void setEventListener(OnEventListener onEventListener) {
        this.mEventListener = onEventListener;
    }

    public void obtainCurrentUseLanguage() {
        Bundle bundle = new Bundle();
        bundle.putInt("command", 11);
        sendBroadcast(bundle);
    }

    public void onEvent(int i, Bundle bundle) {
        switch (i) {
            case 100:
                onReconnectTXZ();
                return;
            case 101:
                onInit(true);
                return;
            case 102:
                onInit(false);
                return;
            default:
                switch (i) {
                    case 107:
                        String string = bundle.getString("text");
                        String string2 = bundle.getString(Constants.KEY_ONWAKEUP_JSON);
                        OnWakeupListener onWakeupListener = this.mListener;
                        if (onWakeupListener != null) {
                            onWakeupListener.onWakeUp(string, string2);
                        }
                        return;
                    case 108:
                        TxzAsrManager.getInstance().onCommand(bundle.getString("data"), bundle.getString("cmd"));
                        return;
                    case 109:
                        onLanguageChange(bundle.getString("vi"));
                        return;
                    case 110:
                        String string3 = bundle.getString("buildInfo");
                        if (!TextUtils.isEmpty(string3)) {
                            OnEventListener onEventListener = this.mEventListener;
                            if (onEventListener != null) {
                                onEventListener.onEvent(202, string3);
                            }
                        }
                        return;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("no match callback id:");
                        sb.append(i);
                        Log.d(TAG, sb.toString());
                        return;
                }
        }
    }

    private void onLanguageChange(String str) {
        if (!TextUtils.isEmpty(str)) {
            OnEventListener onEventListener = this.mEventListener;
            if (onEventListener != null) {
                onEventListener.onEvent(201, str);
            }
        }
    }
}
