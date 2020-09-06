package roxwin.tun.baseui.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

object ImageUtil {
    fun genGradientLogin(color: List<String?>): GradientDrawable? {
        return try {
            val color1 = Color.parseColor(color[0])
            val color2 = Color.parseColor(color[1])
            //            int color3 = Color.parseColor(color.get(2));
            val gd = GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(color1, color2)
            )
            gd.gradientType = GradientDrawable.LINEAR_GRADIENT
            gd.setGradientCenter(0.6f, 1.0f)
            gd
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun renGradientButton(color: List<String?>): GradientDrawable? {
        return try {
            val color1 = Color.parseColor(color[0])
            val color2 = Color.parseColor(color[1])
            //            int color3 = Color.parseColor(color.get(2));
            val gd = GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(color1, color2 /*, color3*/)
            )
            gd
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun genGradient4Corner(
            color: List<String?>,
            radius: Float
    ): GradientDrawable? {
        return try {
            val color1 = Color.parseColor(color[0])
            val color2 = Color.parseColor(color[1])
            //            int color3 = Color.parseColor(color.get(2));
            val gd = GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(color1, color2 /*, color3*/)
            )
            gd.cornerRadius = radius
            gd
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun gen4Corner(color: String?, radius: Float): GradientDrawable? {
        return try {
            val color1 = Color.parseColor(color)
            val gd = GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(color1, color1, color1)
            )
            gd.cornerRadius = radius
            gd
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun genGradientRightCorner(
            color: List<String?>,
            radius: Float
    ): GradientDrawable? {
        return try {
            val color1 = Color.parseColor(color[0])
            val color2 = Color.parseColor(color[1])
            //            int color3 = Color.parseColor(color.get(2));
            val gd = GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(color1, color2 /*, color3*/)
            )
            gd.cornerRadii = floatArrayOf(0f, 0f, radius, radius, radius, radius, 0f, 0f)
            gd
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}