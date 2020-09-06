package kun.ktupdatelibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

class CheckUpdateTask extends AsyncTask<Void, Void, String> {
    private ProgressDialog dialog;
    private Activity mContext;
    private int mType;
    private boolean mShowProgressDialog;
    private String url;
    private boolean forceUpdate;
    private String message;

    CheckUpdateTask(Activity context, int type, boolean showProgressDialog, String url, boolean forceUpdate, String message) {
        this.mContext = context;
        this.mType = type;
        this.mShowProgressDialog = showProgressDialog;
        this.url = url;
        this.forceUpdate = forceUpdate;
        this.message = message;
    }

    protected void onPreExecute() {
        if (mShowProgressDialog) {
            dialog = new ProgressDialog(mContext);
            dialog.setCancelable(false);
            dialog.setMessage(mContext.getString(R.string.android_auto_update_dialog_checking));
            dialog.show();
        }
    }


    @Override
    protected void onPostExecute(String result) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        showDialog(result);
    }

    private void showDialog(String result) {
        try {
            showDialog(mContext, message, result, forceUpdate);
        } catch (Exception e) {
            Log.e(UpdateConstants.TAG, "parse json error");
        }
    }


    /**
     * Show dialog
     */
    private void showDialog(Activity context, String content, String apkUrl,boolean forceUpdate) {
        UpdateDialog.show(context, content, apkUrl,forceUpdate);
    }


    @Override
    protected String doInBackground(Void... args) {
        return url;
    }
}
