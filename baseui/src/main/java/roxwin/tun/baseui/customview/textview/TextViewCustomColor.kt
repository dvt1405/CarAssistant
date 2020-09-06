//package roxwin.tun.baseui.customview.textview
//
//import android.content.Context
//import android.graphics.Color
//import android.util.AttributeSet
//import androidx.annotation.ColorInt
//import androidx.annotation.NonNull
//import androidx.annotation.Nullable
//import androidx.appcompat.widget.AppCompatTextView
//import roxwin.tun.baseui.R
//
//abstract class TextViewCustomColor : AppCompatTextView {
//    private var colorType: ColorType? = null
//    constructor(context: Context) : super(context)
//    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        context,
//        attrs,
//        defStyleAttr
//    )
//
//    fun init(attrs: AttributeSet?, defStyleAttr: Int) {
//        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewCustomColor)
//        colorType = colorTypes[typeArray.getInt(R.styleable.TextViewCustomColor_type, 0)]
//        setColor(colorType!!)
//        typeArray.recycle()
//    }
//
//    fun setTextColor(color: String) {
//        try {
//            setTextColor(Color.parseColor(color))
//        } catch (e: Exception) {
//            throw e
//        }
//    }
//
//    fun setTextColor() {
//        color?.let {
//            setTextColor(it)
//        }
//    }
//
//    abstract fun setColor(colorType: ColorType)
//    abstract var color: String?
//
//    enum class ColorType(val value: Int) {
//        MAIN(1),
//        TITLE(3),
//        DESCRIPTION(0),
//        HIGHLIGHTED(2),
//        SEPARATOR(4),
//        SECTION_HEADER(5)
//    }
//
//    companion object {
//        private val colorTypes = arrayOf(
//            ColorType.DESCRIPTION,
//            ColorType.MAIN,
//            ColorType.HIGHLIGHTED,
//            ColorType.TITLE,
//            ColorType.SEPARATOR,
//            ColorType.SECTION_HEADER
//        )
//    }
//}