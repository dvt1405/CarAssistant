package roxwin.tun.baseui.utils

import android.annotation.SuppressLint
import android.util.Log

@SuppressLint("LogNotTimber")
object LogUtil {
    private const val ERROR_TAG = "Error"
    private const val DEBUG_TAG = "DEBUG"
    private const val INFO_TAG = "INFO"
    private const val NULL = "No Message"

    @SuppressLint("LogNotTimber")
    fun e(t: Throwable) {
        Log.e(ERROR_TAG, t.message, t)
    }

    fun e(message: String?) {
        Log.e(ERROR_TAG, message ?: NULL)
    }

    fun d(message: String?) {
        Log.e(ERROR_TAG, message ?: NULL)
    }
}