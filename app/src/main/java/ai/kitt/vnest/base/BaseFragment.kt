package ai.kitt.vnest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes val layoutRes: Int) : Fragment(layoutRes) {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(requireView())
        initAction(requireView())
    }

    abstract fun initView(view: View)
    abstract fun initAction(view: View)
}