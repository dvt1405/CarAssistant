package roxwin.tun.baseui.multiSwipeRefreshLayout

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.view.MotionEventCompat
import androidx.core.view.ViewCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs

open class MultiSwipeRefreshLayout : SwipeRefreshLayout {
    constructor(context: Context) : super(context)
    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet) : super(context, attrs)

    //Illegal button
    private val INVALID_POINTER = -1f

    //dispatch method records the x pressed the first time
    private var mInitialDisPatchDownX: Float? = null

    //dispatch method records the first press of y
    private var mInitialDisPatchDownY: Float? = null

    //dispatch method to record the finger
    private var mActiveDispatchPointerId = INVALID_POINTER.toInt()

    // Whether to request interception
    private var hasRequestDisallowIntercept: Boolean = false

    override fun requestDisallowInterceptTouchEvent(b: Boolean) {
        hasRequestDisallowIntercept = b
        // Nope.
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mActiveDispatchPointerId = MotionEventCompat.getPointerId(ev, 0)
                val initialDownX = getMotionEventX(ev, mActiveDispatchPointerId)
                if (initialDownX != INVALID_POINTER) {
                    mInitialDisPatchDownX = initialDownX
                }
                val initialDownY = getMotionEventY(ev, mActiveDispatchPointerId)
                if (mInitialDisPatchDownY != INVALID_POINTER) {
                    mInitialDisPatchDownY = initialDownY
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (hasRequestDisallowIntercept) {
                    // Solve the viewPager sliding conflict problem
                    val x = getMotionEventX(ev, mActiveDispatchPointerId)
                    val y = getMotionEventY(ev, mActiveDispatchPointerId)
                    if (mInitialDisPatchDownX != INVALID_POINTER && x != INVALID_POINTER &&
                        mInitialDisPatchDownY != INVALID_POINTER && y != INVALID_POINTER
                    ) {
                        val xDiff = abs(x - mInitialDisPatchDownX!!)
                        val yDiff = abs(y - mInitialDisPatchDownY!!)
                        if (xDiff > ViewConfiguration.get(context).scaledTouchSlop && xDiff * 0.7f > yDiff) {
                            // Horizontal scrolling does not need to intercept
                            super.requestDisallowInterceptTouchEvent(true)
                        } else {
                            super.requestDisallowInterceptTouchEvent(false)
                        }
                    } else {
                        super.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_CANCEL) {
                    hasRequestDisallowIntercept = false
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun getMotionEventY(ev: MotionEvent, activePointerId: Int): Float {
        val index = MotionEventCompat.findPointerIndex(ev, activePointerId)
        if (index < 0) {
            return -1f
        }
        return MotionEventCompat.getY(ev, index)
    }

    private fun getMotionEventX(ev: MotionEvent, activePointerId: Int): Float {
        val index = MotionEventCompat.findPointerIndex(ev, activePointerId)
        if (index < 0) {
            return -1f
        }
        return MotionEventCompat.getX(ev, index)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
    }

    override fun setRefreshing(refreshing: Boolean) {
        super.setRefreshing(refreshing)
    }

    override fun setOnRefreshListener(listener: OnRefreshListener?) {
        super.setOnRefreshListener(listener)
    }
}