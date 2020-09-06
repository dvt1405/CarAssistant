package roxwin.tun.baseui.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.Printer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.textfield.TextInputLayout
import roxwin.tun.baseui.R
import roxwin.tun.baseui.utils.ImageUtil.renGradientButton
import java.lang.Exception

object BindingAdapter {
    private const val HTTP = "http"

    @JvmStatic
    @BindingAdapter("visibleWhen")
    fun visibleWhen(view: View, show: Boolean) {
        Log.d(" visible = ", "$show")
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("invisibleWhen")
    fun invisibleWhen(view: View, hide: Boolean) {
        view.visibility = if (hide) View.INVISIBLE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("swipeRefreshShow")
    fun swipeRefreshShow(view: SwipeRefreshLayout, show: Boolean) {
        view.isRefreshing = show
    }

    @JvmStatic
    @BindingAdapter("swipeRefreshEnable")
    fun swipeRefreshEnable(view: SwipeRefreshLayout, show: Boolean) {
        view.isEnabled = show
    }

    @JvmStatic
    @BindingAdapter("bindImageUrl")
    fun bindImageUrl(image: ImageView, url: String?) {
        if (url == null || url.isEmpty()) {
            image.setImageResource(R.drawable.no_image_available)
            return
        }
        //        image.setImageResource(R.drawable.no_image_available);
        val requestOptionsListImage = RequestOptions
            .diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .override(400, 400)
            .placeholder(R.drawable.no_image_available)
            .centerCrop()
            .dontAnimate()
            .dontTransform()


        Glide.with(image.context)
            .load(url)
            .apply(requestOptionsListImage)
//            .apply { RequestOptions.bitmapTransform(RoundedCorners(8)) }
            .into(image)
    }

    fun bindImageUrl(image: ImageView, url: String?, @DrawableRes iconDefault: Int) {
        val padding = image.context.resources.displayMetrics.scaledDensity * 8
        if (url.isNullOrEmpty() || !url.contains(HTTP)) {
            image.setPadding(padding.toInt(), padding.toInt(), padding.toInt(), padding.toInt())
            image.setImageResource(iconDefault)
            return
        }
        val requestOptionsListImage = RequestOptions
            .diskCacheStrategyOf(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.no_image_available)
            .centerCrop()
            .dontAnimate()
            .dontTransform()

        Glide.with(image.context)
            .load(url)
            .apply(requestOptionsListImage)
//            .apply { RequestOptions.bitmapTransform(RoundedCorners(8)) }
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    image.setPadding(
                        padding.toInt(),
                        padding.toInt(),
                        padding.toInt(),
                        padding.toInt()
                    )
                    image.setImageResource(iconDefault)
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(image)

    }

    @JvmStatic
    @SuppressLint("RestrictedApi")
    @BindingAdapter("bindButtonTintCheckBox")
    fun bindButtonTintCheckBox(checkBox: AppCompatCheckBox?, color: String?) {
        if (checkBox == null) return
        if (color == null) return
        try {
            checkBox.supportButtonTintList = ColorStateList.valueOf(
                Color.parseColor(
                    color
                )
            )
        } catch (e: IllegalArgumentException) {
        }
    }

    @JvmStatic
    @BindingAdapter("bindGradientBackgroundColor")
    fun bindGradientBackgoundColor(
        view: View?,
        color: List<String?>?
    ) {
        if (view == null) return
        if (color == null) return
        if (color.size != 2) return
        try {
            view.background = renGradientButton(color)
        } catch (e: IllegalArgumentException) {
            view.setBackgroundColor(Color.parseColor("#000000"))
        }
    }

//    @JvmStatic
//    @BindingAdapter("bindFont")
//    fun bindFontText(textView: TextView?, type: Int) {
//    }

    @JvmStatic
    @BindingAdapter("bindFontTextHint")
    fun bindFontTextHint(inputLayout: TextInputLayout?, type: Int) {
    }

    @JvmStatic
    @BindingAdapter("bindFontToolbar")
    fun bindFontTextToView(view: Toolbar?, type: Int) {
    }
}