package roxwin.tun.baseui.dialog

import android.os.SystemClock
import android.view.View
import android.widget.TextView
import kotlin.reflect.KFunction1

/*****
 * Implement to fix the double click problem.
 * Avoid the fast double click for button and images.
 */
abstract class OnSingleClickListener : View.OnClickListener {
    private var prevClickTime: Long = 0
    override fun onClick(v: View) {
        _onClick(v)
    }

    @Synchronized
    private fun _onClick(v: View) {
        val current = SystemClock.elapsedRealtime()
        if (current - prevClickTime > gap) {
            onSingleClick(v)
            prevClickTime = SystemClock.elapsedRealtime()
        }
    }

    abstract fun onSingleClick(v: View?)

    /********
     *
     * @return The time in ms between two clicks.
     */
    private val gap: Long = 1000L
}

fun TextView.setOnSingleClickListener(onSingleClickListener: (view: View?) -> Unit) {
    setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {
            onSingleClickListener(v)
        }
    })
}

fun View.setOnSingleClickListener(onSingleClickListener: (view: View?) -> Unit) {
    setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {
            onSingleClickListener(v)
        }
    })
}

fun View.setOnSingleClickListener(onSingleClickListener: OnSingleClickListener) {
    setOnClickListener {
        onSingleClickListener.onSingleClick(it)
    }
}

fun View.setOnSingleClickListener(kFunction1: KFunction1<View, Unit>) {
    setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {
            kFunction1(v!!)
        }
    })
}
