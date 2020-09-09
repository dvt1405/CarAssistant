package ai.kitt.vnest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import ai.kitt.vnest.R;
import ai.kitt.vnest.basedata.database.sharepreference.VnestSharePreference;
import ai.kitt.vnest.util.DialogUtils;
import ai.kitt.vnest.util.NavigationUtil;

public class OpenNavigationReceiver extends BroadcastReceiver {
    public static final String ACTION_APP_NOT_INSTALLED = "OPEN_GOOGLE_MAP";
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
            DialogUtils.getConfirmDialog(context, context.getString(R.string.alert_app_no_installed),
                    context.getString(R.string.alert_app_no_installed_message), true, dialogInterface -> {
                        String location = intent.getStringExtra(EXTRA_LOCATION);
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        VnestSharePreference.getInstance(context).saveMapAppId(NavigationUtil.MAPS_GOOGLE_MAP_APP_ID);
                        context.startActivity(mapIntent);
                    }).show();
        }
    }

}
