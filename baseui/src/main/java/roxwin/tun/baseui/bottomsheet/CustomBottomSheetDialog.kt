package roxwin.tun.baseui.bottomsheet

import android.content.Context
import android.content.DialogInterface
import android.view.Window
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetDialog

class CustomBottomSheetDialog : BottomSheetDialog {
    constructor(context: Context) : super(context)

    constructor(context: Context, @StyleRes theme: Int) : super(context, theme)

    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener)
}