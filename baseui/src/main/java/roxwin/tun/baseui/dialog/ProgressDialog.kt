package roxwin.tun.baseui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.Window
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_progress.view.*
import roxwin.tun.baseui.R

class ProgressDialog(private val context: Context, cancelable: Boolean) {
    private val progressBar: ProgressBar by lazy {
        view.pg
    }
    private val view by lazy {
        LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false)
    }

    @SuppressLint("ResourceAsColor")
    constructor(context: Context, cancelable: Boolean, @ColorRes color: Int) : this(
        context,
        cancelable
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            progressBar.indeterminateDrawable = ColorDrawable(context.getColor(color))
        }
    }

    private var progress: AlertDialog = AlertDialog.Builder(context, R.style.waiting_dialog)
        .setView(
            view
        )
        .setCancelable(cancelable)
        .create()

    fun show() {
        progress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progress.show()
    }

    fun dismiss() {
        progress.dismiss()
    }
}