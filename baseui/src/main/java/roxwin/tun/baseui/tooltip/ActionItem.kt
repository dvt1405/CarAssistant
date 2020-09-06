package roxwin.tun.baseui.tooltip

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

data class ActionItem(
    val icon: Drawable,
    val thumb: Bitmap,
    val title: String,
    val isSelected: Boolean
)