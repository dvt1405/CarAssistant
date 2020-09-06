package ai.kitt.vnest.feature.screencheckcarvilolations

import ai.kitt.vnest.App
import ai.kitt.vnest.R
import ai.kitt.vnest.base.BaseFragment
import ai.kitt.vnest.feature.activitymain.MainActivity
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import com.google.android.gms.vision.text.TextRecognizer
import kotlinx.android.synthetic.main.fragment_web_view.view.*

class WebViewFragment : BaseFragment(R.layout.fragment_web_view) {
    companion object {
        const val EXTRA_URL = "extra_url"

        @JvmField
        val TAG: String = WebViewFragment::class.java.name

        @JvmStatic
        fun startThis(url: String?): WebViewFragment = WebViewFragment().apply {
            val bundle = arguments ?: Bundle()
            bundle.putString(EXTRA_URL, url)
            arguments = bundle
        }
    }

    val textRecognizer = TextRecognizer.Builder(App.get()).build()
    val handle = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            val what = msg?.what
            if (what == 1) {

            }
        }
    }
    val url: String by lazy { arguments?.getString(EXTRA_URL)!! }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(view: View) {
        view.webView.settings.javaScriptEnabled = true
        view.webView.addJavascriptInterface(WebViewJavaScriptInterface(this), WebViewJavaScriptInterface.JAVA_SCRIPT_INTERFACE_FUNC)
        view.webView.webViewClient = WebViewClient(requireContext())
    }

    override fun initAction(view: View) {
        view.webView.loadUrl(url)
    }

    fun onSuccess(result: String) {
        (requireActivity() as MainActivity).sendMessage(result, false)
        (requireActivity() as MainActivity).speak(result, false)
    }
}