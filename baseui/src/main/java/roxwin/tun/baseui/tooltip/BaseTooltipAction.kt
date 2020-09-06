package roxwin.tun.baseui.tooltip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.annotation.LayoutRes
import roxwin.tun.baseui.R

class BaseTooltipAction(private val context: Context) {
    private var baseTooltip: BaseTooltip = BaseTooltip(context)
    private var _onTooltipDismissListener: PopupWindow.OnDismissListener? = null
    var onTooltipDismissListener: PopupWindow.OnDismissListener?
        get() = _onTooltipDismissListener
        set(value) {
            _onTooltipDismissListener = onTooltipDismissListener
        }

    @LayoutRes
    private var tooltipLayoutRes: Int? = R.layout.default_tooltip

    constructor(context: Context, @LayoutRes layoutRes: Int) : this(context) {
        this.tooltipLayoutRes = layoutRes
    }

    init {
        if (tooltipLayoutRes == null) {
            tooltipLayoutRes = R.layout.default_tooltip
        }
        baseTooltip.rootView = LayoutInflater.from(context).inflate(tooltipLayoutRes!!, null, false)
        baseTooltip.popupWindow?.setOnDismissListener {
            _onTooltipDismissListener?.onDismiss()
        }
    }

    fun setOnTooltipDismissListener(listener: () -> Unit) {
        _onTooltipDismissListener = PopupWindow.OnDismissListener { listener() }
    }

    fun show(anchorView: View) {
        baseTooltip.show(anchorView)
    }

    fun dismiss() {
        baseTooltip.dismiss()
    }
}