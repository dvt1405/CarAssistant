package roxwin.tun.baseui.tablayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.default_tooltip.view.*
import roxwin.tun.baseui.R
import java.lang.reflect.Field

class CustomTabLayout : TabLayout {
    companion object {
        private const val SCROLLABLE_TAB_MIN_WIDTH = "scrollableTabMinWidth"

    }

    private var defaultTabDivider: Int = 3

    init {
        initTabMinWidth(defaultTabDivider)
    }

    constructor(context: Context) : super(context) {
        initTabMinWidth(defaultTabDivider)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setUpAttributeSet(attrs)
        initTabMinWidth(defaultTabDivider)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setUpAttributeSet(attrs, defStyleAttr)
        initTabMinWidth(defaultTabDivider)
    }

    private fun setUpAttributeSet(attrs: AttributeSet?) {
        val typeArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomTabLayout, 0, 0)
        defaultTabDivider = typeArray.getInt(R.styleable.CustomTabLayout_tabMinWidthDivider, 3)
        typeArray.recycle()
    }

    private fun setUpAttributeSet(attrs: AttributeSet?, defStyleAttr: Int) {
        val typeArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTabLayout,
            defStyleAttr,
            0
        )
        defaultTabDivider = typeArray.getInt(R.styleable.CustomTabLayout_tabMinWidthDivider, 3)
        typeArray.recycle()
    }

    private fun initTabMinWidth(divider: Int) {
        val tabMinWidth = context.resources.displayMetrics.widthPixels / divider
        val field: Field
        try {
            field = TabLayout::class.java.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH)
            field.isAccessible = true
            field.set(this, tabMinWidth)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}