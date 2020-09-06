package roxwin.tun.baseui.utils

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment

object KeyboardUtil {
    @JvmStatic
    fun hideSoftKeyboard(activity: Activity?) {
        if (activity == null) return
        val inputMethodManager =
            activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        val currentFocus = activity.currentFocus
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
    @JvmStatic
    fun hideSoftKeyboard(dialogFragment: DialogFragment?) {
        if (dialogFragment == null) return
        val activity = dialogFragment.activity ?: return
        val inputMethodManager =
            activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        val currentFocus = dialogFragment.view
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    @JvmStatic
    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    fun setupHideKeyboarOutTouchScreen(activity: Activity?, view: View?) {
        if (activity == null) return
        if (view == null) return
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener(OnTouchListener { v: View?, event: MotionEvent? ->
                hideSoftKeyboard(activity)
                false
            })
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupHideKeyboarOutTouchScreen(
                    activity,
                    innerView
                )
            }
        }
    }

    fun setupHideKeyboarOutTouchScreen(
        dialogFragment: DialogFragment?,
        view: View?
    ) {
        if (dialogFragment == null) return
        if (view == null) return
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener(OnTouchListener { v: View?, event: MotionEvent? ->
                hideSoftKeyboard(
                    dialogFragment
                )
                false
            })
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupHideKeyboarOutTouchScreen(
                    dialogFragment,
                    innerView
                )
            }
        }
    }
}