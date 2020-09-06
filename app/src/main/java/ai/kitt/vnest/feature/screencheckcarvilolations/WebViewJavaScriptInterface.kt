package ai.kitt.vnest.feature.screencheckcarvilolations

import android.os.Message
import android.webkit.JavascriptInterface
import org.jsoup.Jsoup

class WebViewJavaScriptInterface(val fragment: WebViewFragment) {
    companion object{
        var callTimes = 0
        const val JAVA_SCRIPT_INTERFACE_FUNC = "getHtml"
    }
    @JavascriptInterface
    fun showHTML(html: String) {
        callTimes++
        val document = Jsoup.parse(html)
        val result = document.select("#bodyPrint")[0]
        val src = document.select(".flex")[0].select("img")[0].attr("src")
        if(callTimes >1) {
            fragment.onSuccess(result.text())
        }
        val message = Message()
        message.what = 1
        message.obj = src
        message.target = fragment.handle
        message.sendToTarget()
    }
}