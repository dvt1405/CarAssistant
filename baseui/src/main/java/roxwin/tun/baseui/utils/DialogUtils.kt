package roxwin.tun.baseui.utils;

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AlertDialog
import roxwin.tun.baseui.R

object DialogUtils {
    @JvmStatic
    fun showProgressDialog(context: Context, cancelable: Boolean): AlertDialog {
        val progress: AlertDialog =
            AlertDialog.Builder(context, R.style.waiting_dialog)
                .setView(
                    LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false)
                )
                .setCancelable(cancelable)
                .create()
        val window: Window? = progress.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progress.show()
        return progress
    }

    interface ConfirmDialogListener {
        fun allowClick()
        fun cancelClick()
    }

}