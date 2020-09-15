package ai.kitt.vnest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import ai.kitt.vnest.R;
import ai.kitt.vnest.basedata.database.sharepreference.VnestSharePreference;
import ai.kitt.vnest.basedata.entity.Poi;
import ai.kitt.vnest.util.ConfirmDialog;
import ai.kitt.vnest.util.LogUtil;
import ai.kitt.vnest.util.NavigationUtil;

public class OpenNavigationReceiver extends BroadcastReceiver {
    public static final String ACTION_APP_NOT_INSTALLED = "OPEN_GOOGLE_MAP";
    public static final String ACTION_SEND_VIET_MAP_BROAD_CAST = "SEND_VIETMAP_BROADCAST";
    public static final String EXTRA_LOCATION = "extra:location";

    public static OpenNavigationReceiver startThis(Context context) {
        OpenNavigationReceiver broadCast = new OpenNavigationReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_APP_NOT_INSTALLED);
        context.registerReceiver(broadCast, intentFilter);
        return broadCast;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equalsIgnoreCase(ACTION_APP_NOT_INSTALLED)) {
            ConfirmDialog confirmDialog;
            confirmDialog = new ConfirmDialog.Builder(context, false)
                    .title(context.getString(R.string.alert_app_no_installed))
                    .message(context.getString(R.string.alert_app_no_installed_message))
                    .setOnAllowClick(dialog -> {
                        navigateToPoint(context, intent);
                    }).build();
            confirmDialog.show();
            new Handler().postDelayed(() -> {
                navigateToPoint(context, intent);
            }, 3000);
            return;
        }
        if (action != null && action.equalsIgnoreCase(ACTION_SEND_VIET_MAP_BROAD_CAST)) {
            try {
                Poi poi = intent.getParcelableExtra(EXTRA_LOCATION);
                new Handler().postDelayed(() -> context.sendBroadcast(NavigationUtil.buildVietMapIntent(poi)), 5000);
            } catch (Exception e) {
                LogUtil.log(e);
                FirebaseCrashlytics.getInstance().recordException(e);
            }
        }

    }

    public void navigateToPoint(Context context, Intent intent) {
        String location = intent.getStringExtra(EXTRA_LOCATION);
        VnestSharePreference.getInstance(context).saveMapAppId(NavigationUtil.MAPS_GOOGLE_MAP_APP_ID);
        NavigationUtil.navigationToLocation(location, context);
    }

}
