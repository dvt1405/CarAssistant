package ai.kitt.vnest.basedata.database.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import ai.kitt.vnest.util.NavigationUtil;

public class VnestSharePreference {
    private static VnestSharePreference sharePreference;
    private final static String SHARE_PREF_NAME = "Vnest";
    private final static String MAP_APP_ID = "Map_app_id";
    private final static String ACTIVE_NAME = "Acitve_code";
    private static final String WAS_ACTIVE_CODE = "extra_was_active_code";
    private static final String BKS = "bks";
    private SharedPreferences sharedPreferences;
    private Context context;

    private VnestSharePreference(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static VnestSharePreference getInstance(Context context) {
        if (sharePreference == null) {
            sharePreference = new VnestSharePreference(context);
        }
        return sharePreference;
    }

    public boolean isHadActiveCode() {
        return getActiveCode() != null;
    }

    public void saveActiveCode(@NonNull String activeCode) {
        sharedPreferences.edit()
                .putString(ACTIVE_NAME, activeCode)
                .apply();
    }

    public String getActiveCode() {
        return sharedPreferences.getString(ACTIVE_NAME, null);
    }

    public void saveActiveCode(@NonNull boolean wasActiveCode) {
        sharedPreferences.edit()
                .putBoolean(WAS_ACTIVE_CODE, wasActiveCode)
                .apply();

    }

    public boolean wasActiveCode() {
        return sharedPreferences.getBoolean(WAS_ACTIVE_CODE, false);
    }

    public String getMapAppId() {
        return sharedPreferences.getString(MAP_APP_ID, NavigationUtil.MAPS_GOOGLE_MAP_APP_ID);
    }

    public void saveMapAppId(String appId) {
        sharedPreferences.edit()
                .putString(MAP_APP_ID, appId)
                .apply();
    }
    public String getBks() {
        return sharedPreferences.getString(BKS, "");
    }

    public void saveBks(String bks) {
        sharedPreferences.edit()
                .putString(BKS, bks)
                .apply();
    }
}
