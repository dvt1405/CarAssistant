//package roxwin.tun.baseui
//
//import android.app.AlertDialog
//import android.content.Context
//import android.util.AttributeSet
//import androidx.preference.ListPreference
//
//class DListPref : ListPreference {
//    interface LoadingListener {
//        fun setData(lp: ListPreference?)
//    }
//
//    var TheLL: LoadingListener? = null
//    fun setLoadingListener(l: LoadingListener?) {
//        TheLL = l
//    }
//
//    override  fun onPrepareDialogBuilder(builder: AlertDialog.Builder?) {
//        if (TheLL != null) {
//            TheLL!!.setData(this)
//        }
//        super.onPrepareDialogBuilder(builder)
//    }
//
//    //Do not mind the rest of this class, as they are auto-generated boilerplate code.
//    constructor(
//        context: Context?,
//        attrs: AttributeSet?,
//        defStyleAttr: Int,
//        defStyleRes: Int
//    ) : super(context, attrs, defStyleAttr, defStyleRes) {
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        context,
//        attrs,
//        defStyleAttr
//    ) {
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
//    constructor(context: Context?) : super(context) {}
//}