package roxwin.tun.baseui.recyclerview

import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView

abstract class CommonViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    var currentVelocity = 0f

    val rotation = SpringAnimation(itemView, SpringAnimation.ROTATION)
            .setSpring(SpringForce()
                    .setFinalPosition(0f)
                    .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_LOW))
            .addUpdateListener { animation, value, velocity ->
                currentVelocity = velocity
            }
    val translationY: SpringAnimation = SpringAnimation(itemView, SpringAnimation.TRANSLATION_Y)
            .setSpring(
                    SpringForce()
                            .setFinalPosition(0f)
                            .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                            .setStiffness(SpringForce.STIFFNESS_LOW)
            )

    fun onBind(item: T, onItemClick: (item: T, position: Int) -> Unit) {
        onBind(item)
        itemView.setOnClickListener {
            onItemClick(item, adapterPosition)
        }
    }

    abstract fun onBind(item: T)
}