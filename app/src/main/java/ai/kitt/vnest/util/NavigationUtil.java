package ai.kitt.vnest.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.IntentUtils;

import java.util.concurrent.atomic.AtomicReference;

import ai.kitt.vnest.basedata.database.sharepreference.VnestSharePreference;
import ai.kitt.vnest.basedata.entity.Poi;
import ai.kitt.vnest.receiver.OpenNavigationReceiver;

@SuppressLint("All")
public class NavigationUtil {
    public static final String MAPS_NATIVEL_APP_ID = "com.navitel";
    public static final String MAPS_GOOGLE_MAP_APP_ID = "com.google.android.apps.maps";
    public static final String MAPS_VIET_MAP_APP_ID = "com.vietmap.s1OBU";

    public static Intent buildVietMapIntent(Poi paramAddress) {
        Intent intent = new Intent();
        intent.setAction("PAPAGO_BROADCAST_RECV");
        intent.putExtra("KEY_TYPE", 10038);
        intent.putExtra("POINAME", paramAddress.getTitle());
        intent.putExtra("LAT", paramAddress.getGps().getLatitude());
        intent.putExtra("LON", paramAddress.getGps().getLongitude());
        intent.putExtra("DEV", 0);
        intent.putExtra("STYLE", 0);
        intent.putExtra("SOURCE_APP", "third-party");
        return intent;
    }

    public static void openVietMap(Poi poi, Context context) {
        if (!AppUtils.isAppInstalled(MAPS_VIET_MAP_APP_ID)) {
            String location = poi.getGps().getLatitude() + "," + poi.getGps().getLongitude();
            navigationToLocation(location, context);
        } else if (AppUtil.isAppAlive(MAPS_VIET_MAP_APP_ID)) {
            context.sendBroadcast(buildVietMapIntent(poi));
        } else {
            AppUtils.launchApp(MAPS_VIET_MAP_APP_ID);
            context.sendBroadcast(new Intent());
            try {
                Intent vietMapBroadCast = new Intent(context, OpenNavigationReceiver.class);
                vietMapBroadCast.putExtra(OpenNavigationReceiver.EXTRA_LOCATION, poi);
                context.sendBroadcast(vietMapBroadCast);
            } catch (Exception ex) {
                LogUtil.log(ex);
            }
        }
    }

    public static void navigationOtPointByName(Double latitude, Double longitude, Context context) {
        Uri intentUri = Uri.parse("google.navigation:ll" + latitude + "," + longitude);
        Intent routeIntent = new Intent(Intent.ACTION_VIEW, intentUri);
        String packageName = VnestSharePreference.getInstance(context).getMapAppId();
        routeIntent.setPackage(packageName);
        if (routeIntent.resolveActivity(context.getPackageManager()) == null) {
            // Navitel is not installed, open the page in the market
            intentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
            routeIntent = new Intent(Intent.ACTION_VIEW, intentUri);
            routeIntent.setPackage("com.google.android.apps.maps");
        }
        context.startActivity(routeIntent);
    }

    public static void navigationToPoint(Poi poi, Context context) {
        String location = poi.getGps().getLatitude() + "," + poi.getGps().getLongitude();
        String packageName = VnestSharePreference.getInstance(context).getMapAppId();
        if (packageName.equalsIgnoreCase(MAPS_VIET_MAP_APP_ID)) {
            openVietMap(poi, context);
        } else {
            navigationToLocation(location, context);
        }
    }

    public static void navigationToLocation(String location, Context context) {
        AtomicReference<Uri> gmmIntentUri = new AtomicReference<>(Uri.parse("google.navigation:ll=" + location));
        AtomicReference<Intent> mapIntent = new AtomicReference<>(new Intent(Intent.ACTION_VIEW, gmmIntentUri.get()));
        String packageName = VnestSharePreference.getInstance(context).getMapAppId();
        mapIntent.get().setPackage(packageName);
        if (mapIntent.get().resolveActivity(context.getPackageManager()) == null) {
            Intent broadCastIntent = new Intent();
            broadCastIntent.setAction(OpenNavigationReceiver.ACTION_APP_NOT_INSTALLED);
            broadCastIntent.putExtra(OpenNavigationReceiver.EXTRA_LOCATION, location);
            context.sendBroadcast(broadCastIntent);
        } else {
            context.startActivity(mapIntent.get());
        }
    }

    public static void displayPointToMap(Poi poi, Context context) {
        displayLocationToMap(poi.getGps().getLatitude() + "," + poi.getGps().getLongitude() + "(" + poi.getTitle() + ")", context);
    }

    public static void displayLocationToMap(String location, Context context) {
        Uri intentUri = Uri.parse("geo:0,0?q=" + location);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentUri);
        String packageName = VnestSharePreference.getInstance(context).getMapAppId();
        mapIntent.setPackage(packageName);
        if (mapIntent.resolveActivity(context.getPackageManager()) == null) {
            mapIntent = new Intent(Intent.ACTION_VIEW, intentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
        }
        context.startActivity(mapIntent);
    }

    public static void openYoutube(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setPackage("com.vanced.android.youtube");
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            intent.setPackage("com.google.android.youtube");
        }
        try{
            context.startActivity(intent);
        }catch (Exception ex) {
            LogUtil.log(ex);
        }
    }

    public static String getImei(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            String imei = telephonyManager.getDeviceId();
            return imei;
        } catch (Exception e) {
            Log.e("Error", e.getMessage(), e);
            LogUtil.log(e);
        }
        return "";
    }

    public static String getDeviceId(Activity activity) {
        try {
            return Settings.Secure.getString(activity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            Log.e("Error", e.getMessage(), e);
        }
        return "";
    }

    public static boolean checkInternetConnection(Context context) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
    }

    public static void startListenMusic(Context context, String linkMusic) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(linkMusic));
        intent.setPackage("com.zing.mp3");
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkMusic));
        }
        context.startActivity(intent);
    }


    public static Intent getLaunchAppIntent(String str) {
        return IntentUtils.getLaunchAppIntent(str);
    }
}


