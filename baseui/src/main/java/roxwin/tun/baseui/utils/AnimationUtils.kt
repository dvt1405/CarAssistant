package roxwin.tun.baseui.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.default_tooltip.view.*
import roxwin.tun.baseui.R
import kotlin.math.hypot

object AnimationUtils {
    @JvmStatic
    fun getRotateAnimation(isArrowDown: Boolean, width: Int, height: Int): RotateAnimation =
        if (isArrowDown) {
            RotateAnimation(
                0f,
                180f,
                width / 2.toFloat(),
                height / 2.toFloat()
            )
        } else {
            RotateAnimation(
                180f,
                360f,
                width / 2.toFloat(),
                height / 2.toFloat()
            )
        }

}

fun RecyclerView.runLayoutAnimation() {
    val layoutAnimation =
        AnimationUtils.loadLayoutAnimation(context, R.anim.recycler_view_layout_anim_fall_down)
    setLayoutAnimation(layoutAnimation)
    adapter?.notifyDataSetChanged()
    scheduleLayoutAnimation()
}

fun View.startHideOrShowAnimation(shouldShow: Boolean) {
    val repeatAnimView = when {
        shouldShow && visibility == View.VISIBLE -> true
        !shouldShow && visibility == View.INVISIBLE -> true
        else -> false
    }
    if (repeatAnimView) return

    val cx: Int?
    val cy: Int?
    val initRadius: Float?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        cx = width / 2
        cy = height / 2
        initRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
    } else {
        setVisible(shouldShow)
        return
    }
    val anim: Animator
    val startRadius: Float
    val endRadius: Float

    if (shouldShow) {
        startRadius = 0f
        endRadius = initRadius
    } else {
        startRadius = initRadius
        endRadius = 0f
    }

    anim = ViewAnimationUtils.createCircularReveal(
        this,
        cx,
        cy,
        startRadius,
        endRadius
    )
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            setVisible(shouldShow as Boolean?)
        }
    })
    anim.start()

}