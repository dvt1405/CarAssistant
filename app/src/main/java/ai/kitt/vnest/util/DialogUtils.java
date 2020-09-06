package ai.kitt.vnest.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import ai.kitt.vnest.R;

public class DialogUtils {
    static final String CANCEL = "Cancel";
    static final String CONFIRM = "OK";


    public static AlertDialog showProgressDialog(Context context, boolean cancelable) {
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null, false);
        final AlertDialog progress = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(cancelable)
                .create();
        Window window = progress.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progress.show();
        return progress;
    }

    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog waitingDialog = new ProgressDialog(context);
        waitingDialog.setCancelable(false);
        waitingDialog.setMessage(context.getString(R.string.checking_for_update));
        waitingDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        return waitingDialog;
    }

    public static AlertDialog getConfirmDialog(Context context, String title, String message, boolean isShowCancelButton, OnConfirmListener onConfirmListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        if (isShowCancelButton) {
            alertDialogBuilder.setNegativeButton(CANCEL, (dialog, which) -> {
                dialog.dismiss();
            }).setPositiveButton(CONFIRM, (dialog, which) -> {
                onConfirmListener.onConfirm(dialog);
            });
        } else {
            alertDialogBuilder.setPositiveButton(CONFIRM, (dialog, which) -> {
                dialog.dismiss();
            });
        }
        return alertDialogBuilder.create();
    }

    public static AlertDialog getConfirmDialog(Context context, String title, String message, OnConfirmListener onConfirmListener) {
        return getConfirmDialog(context, title, message, false, onConfirmListener);
    }

    public static AlertDialog getConfirmDialog(Context context, String title, String message) {
        return getConfirmDialog(context, title, message, null);
    }

//    public static AlertDialog getActiveDialog(Context context) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//    }

    public interface OnConfirmListener {
        void onConfirm(DialogInterface dialogInterface);
    }
}
