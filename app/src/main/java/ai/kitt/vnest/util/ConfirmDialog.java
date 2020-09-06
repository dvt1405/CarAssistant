package ai.kitt.vnest.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import ai.kitt.vnest.R;

public class ConfirmDialog {
    private AlertDialog alertDialog;

    public ConfirmDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public void show() {
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        this.alertDialog.show();
    }

    public void dismiss() {
        this.alertDialog.dismiss();
    }

    public static class Builder {
        private Context context;
        private AlertDialog alertDialog;
        private AlertDialog.Builder builder;
        private String title;
        private String message;
        private DialogInterface.OnDismissListener onDismissListener;
        private DialogInterface.OnShowListener onShowListener;
        private OnAllowClickListener onAllowClickListener;
        private OnCancelClickListener onCancelClickListener;
        private String btnConfirm;
        private String btnCancel;
        private Boolean cancelable;

        public Builder(Context context, Boolean cancelable) {
            this.context = context;
            this.cancelable = cancelable;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder confirmTitle(String btnConfirm) {
            this.btnConfirm = btnConfirm;
            return this;
        }

        public Builder cancelTitle(String btnCancel) {
            this.btnCancel = btnCancel;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnShowListener(DialogInterface.OnShowListener onShowListener) {
            this.onShowListener = onShowListener;
            return this;
        }

        public Builder setOnAllowClick(OnAllowClickListener onAllowClick) {
            this.onAllowClickListener = onAllowClick;
            return this;
        }

        public Builder setOnCancelClick(OnCancelClickListener onCancelClick) {
            this.onCancelClickListener = onCancelClick;
            return this;
        }

        private AlertDialog createDialog() {
            if (btnConfirm == null) {
                if (btnCancel == null) {
                    btnConfirm = context.getString(R.string.alert_ok_button_title);
                } else {
                    btnConfirm = context.getString(R.string.confirm_button_alert_title);
                }
            }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.Material_Alert_ThemeDefault)
                    .setTitle(title)
                    .setCancelable(cancelable)
                    .setMessage(message)
                    .setOnDismissListener(onDismissListener)
                    .setPositiveButton(btnConfirm, (dialog, which) -> {
                        if (onAllowClickListener != null) {
                            onAllowClickListener.onClick(dialog);
                        } else {
                            dialog.dismiss();
                        }
                    });

            if (btnCancel != null) {
                alertDialogBuilder.setNegativeButton(btnCancel, (dialog, which) -> {
                    if (onCancelClickListener != null) {
                        onCancelClickListener.onClick(dialog);
                    } else {
                        dialog.dismiss();
                    }
                });
            }
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setOnShowListener(onShowListener);
            return alertDialog;
        }

        public ConfirmDialog build() {
            this.alertDialog = createDialog();
            return new ConfirmDialog(this.alertDialog);
        }

        public ConfirmDialog show() {
            this.alertDialog = createDialog();
            ConfirmDialog confirmDialog = new ConfirmDialog(alertDialog);
            confirmDialog.show();
            return confirmDialog;
        }

    }

    public interface OnAllowClickListener {
        void onClick(DialogInterface dialog);
    }

    public interface OnCancelClickListener {
        void onClick(DialogInterface dialog);
    }
}

