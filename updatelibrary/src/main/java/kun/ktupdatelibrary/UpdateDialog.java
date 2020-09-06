package kun.ktupdatelibrary;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

class UpdateDialog {
    static void show(final Activity context, String content, final String downloadUrl, boolean forceUpdate) {
        if (isContextValid(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle(R.string.android_auto_update_dialog_title)
                    .setMessage(content)
                    .setPositiveButton(R.string.android_auto_update_dialog_btn_download, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            goToDownload(context, downloadUrl);
                        }
                    })
                    .setCancelable(false);
            if (!forceUpdate) {
                builder.setNegativeButton(R.string.android_auto_update_dialog_btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
            }
            builder.show();
        }
    }

    private static boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing();
    }


    private static void goToDownload(Activity context, String downloadUrl) {
        Log.e(UpdateConstants.TAG, downloadUrl);
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(UpdateConstants.APK_DOWNLOAD_URL, downloadUrl);
        context.startService(intent);
    }
}
