package roxwin.tun.baseui.tooltip

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import roxwin.tun.baseui.R


open class BaseTooltip(
    private val context: Context
) {
    var popupWindow: PopupWindow? = null
    private var _view: View? = null
    var rootView: View?
        get() = _view
        set(value) {
            _view = value
        }

    constructor(context: Context, popupWindow: PopupWindow, view: View) : this(context, view) {
        this.popupWindow = popupWindow
        this._view = view
    }

    constructor(context: Context, view: View) : this(context) {
        this._view = view
    }

    init {
        if (popupWindow == null) {
            popupWindow = PopupWindow(context)
        }
        if(_view == null) {

        }
        popupWindow!!.setTouchInterceptor { v, event ->
            when (event.action) {
                MotionEvent.ACTION_OUTSIDE -> {
                    popupWindow!!.dismiss()
                    return@setTouchInterceptor true
                }
                MotionEvent.ACTION_BUTTON_PRESS -> {
                    _view!!.performClick()
                    return@setTouchInterceptor true
                }
            }
            return@setTouchInterceptor false
        }
    }

    fun show(anchorView: View) {
        preShow()
        val anchorPosition = IntArray(2)
        val popupWindowWidth = popupWindow?.width
        val popupWindowHeight = popupWindow?.height
        anchorView.getLocationOnScreen(anchorPosition)
        setAnimationStyle(Animation.GROW_FROM_BOTTOM)
        popupWindow?.showAtLocation(anchorView, Gravity.TOP, anchorPosition[0], anchorPosition[1])
    }

    private fun preShow() {
        if (_view == null) throw IllegalStateException("setContentView was not called with a view to display!")
        popupWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow!!.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow!!.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow!!.isTouchable = true
        popupWindow!!.isFocusable = true
        popupWindow!!.isOutsideTouchable = true
        popupWindow!!.contentView = _view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow!!.elevation = 10 * context.resources.displayMetrics.scaledDensity
        }
    }

    fun dismiss() {
        popupWindow!!.dismiss()
    }

    private fun setAnimationStyle(type: Animation) {
        popupWindow?.animationStyle = type.value
    }

    enum class Animation(val value: Int) {
        GROW_FROM_BOTTOM(R.anim.grow_from_bottom),
        GROW_FROM_BOTTOM_LEFT(R.anim.grow_from_bottom_left_to_top_right),
        GROW_FROM_BOTTOM_RIGHT(R.anim.grow_from_bottom_right_to_top_left),
        GROW_FROM_TOP(R.anim.grow_from_top),
        GROW_FROM_TOP_LEFT(R.anim.grow_from_topleft_to_bottomright),
        GROW_FROM_TOP_RIGHT(R.anim.grow_from_topright_to_bottomleft)
    }
}