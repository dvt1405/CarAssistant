package ai.kitt.vnest.speechmanager.speechonline

import ai.kitt.vnest.feature.screenspeech.FragmentResult
import android.os.Bundle
import android.os.Message
import android.speech.SpeechRecognizer
import android.util.Log
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter
import timber.log.Timber


class SpeechRecognitionListener(
        private val mListener: OnResultReady,
        val callback: OnHandleSpeechError
) : RecognitionListenerAdapter() {

    override fun onReadyForSpeech(params: Bundle?) {
    }


    override fun onBufferReceived(buffer: ByteArray?) {
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
    }

    override fun onRmsChanged(rmsdB: Float) {

    }

    override fun onBeginningOfSpeech() {
        callback.onMuteVolume(true)
    }

    override fun onEndOfSpeech() {
        callback.onMuteVolume(false)
    }

    @Synchronized
    override fun onError(error: Int) {
        Log.e("Error",error.toString())
        when (error) {
            SpeechRecognizer.ERROR_NETWORK, SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> {
                callback.onErrorNoNetWork()
            }
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                // send to stop speech record
                callback.onErrorTimeOut()
            }
            else -> {
                callback.onMuteVolume(true)
                callback.onErrorNoMatch()
            }
        }

    }

    override fun onPartialResults(partialResults: Bundle?) {
//        onMuteVolume(false)
        val text = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (partialResults != null) {
            val texts = partialResults.getStringArrayList("android.speech.extra.UNSTABLE_TEXT")
            texts?.let { mListener.onStreamResult(it) }
        }
    }

    override fun onResults(results: Bundle?) {
        callback.onMuteVolume(false)
        if (results != null) {
            val text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            if (text != null) {
                mListener.onResults(text)
            }
        }
    }
    
    interface OnHandleSpeechError{
        fun onErrorNoMatch()
        fun onMuteVolume(shouldMute: Boolean)
        fun onErrorTimeOut()
        fun onErrorNoNetWork()
    }

}