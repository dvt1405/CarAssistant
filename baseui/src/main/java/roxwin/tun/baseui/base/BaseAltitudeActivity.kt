package roxwin.tun.baseui.base

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseAltitudeActivity(@LayoutRes layoutRes: Int) : AppCompatActivity(layoutRes) {
    abstract val actionBarTitle: String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        updateActionBarTitle()
        initView()
        initAction()
    }

    abstract fun initView()
    abstract fun initAction()
    protected fun updateActionBarTitle() {
        supportActionBar?.title = actionBarTitle
    }
}