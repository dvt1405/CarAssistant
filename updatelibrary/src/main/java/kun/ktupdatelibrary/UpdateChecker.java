package kun.ktupdatelibrary;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class UpdateChecker {
    public static void checkForDialog(Activity context, String url, boolean forceUpdate, String message) {
        if (context != null) {
            new CheckUpdateTask(context, UpdateConstants.TYPE_DIALOG, true, url,forceUpdate, message).execute();
        } else {
            Log.e(UpdateConstants.TAG, "The arg context is null");
        }
    }


    public static void checkForNotification(Activity context, String url, boolean cancelable, String message) {
        if (context != null) {
            new CheckUpdateTask(context, UpdateConstants.TYPE_NOTIFICATION, false, url,cancelable, message).execute();
        } else {
            Log.e(UpdateConstants.TAG, "The arg context is null");
        }

    }
}
