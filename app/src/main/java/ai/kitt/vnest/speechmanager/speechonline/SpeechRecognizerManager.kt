package ai.kitt.vnest.speechmanager.speechonline

import ai.kitt.snowboy.service.TriggerOfflineService
import ai.kitt.vnest.App
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer


class SpeechRecognizerManager(
        val context: Context,
        var speechRecognizer: SpeechRecognizer,
        private var onResultReady: OnResultReady,
        var callback: SpeechRecognizerManagerCallBack
) {
    companion object {
        var INSTANCE: SpeechRecognizerManager? = null

        @JvmStatic
        fun getInstance(context: Context, speechRecognizer: SpeechRecognizer, onResultReady: OnResultReady, callback: SpeechRecognizerManagerCallBack): SpeechRecognizerManager {
                INSTANCE = SpeechRecognizerManager(context, speechRecognizer,onResultReady, callback)
            return INSTANCE!!
        }

        @JvmStatic
        fun getInstance() = INSTANCE
    }

    val TAG = "Vnest"
    private var speechIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    private var timeOut = 2000
    private var isListening = false
    val speechListener = SpeechRecognitionListener(
            onResultReady, object : SpeechRecognitionListener.OnHandleSpeechError{
        override fun onErrorNoMatch() {
            if (isListening) {
                recreateVoiceRecord()
            } else {
                muteVolume(false)
            }
        }

        override fun onMuteVolume(shouldMute: Boolean) {
            muteVolume(shouldMute)
        }

        override fun onErrorTimeOut() {
            callback.onErrorTimeOut()
        }

        override fun onErrorNoNetWork() {
            callback.onNoNetWork()
        }
    })

    init {
        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
        speechIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form")
        speechIntent.putExtra("android.speech.extra.LANGUAGE", "vi")
        speechIntent.putExtra("android.speech.extra.PARTIAL_RESULTS", true)
        speechIntent.putExtra("android.speech.action.RECOGNIZE_SPEECH", "android.speech.extra.PREFER_OFFLINE")
        speechIntent.putExtra("android.speech.extras.SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS", timeOut)
        speechIntent.putExtra("android.speech.extras.SPEECH_INPUT_MINIMUM_LENGTH_MILLIS", 5000)
        speechIntent.putExtra(
                RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,
                timeOut
        )
        speechRecognizer.setRecognitionListener(speechListener)
    }

    fun restartListening() {
        muteVolume(true)
        speechRecognizer.stopListening()
        speechRecognizer.startListening(speechIntent)
        isListening = true
    }

    fun startListening() {
        TriggerOfflineService.stopService(App.get())
            try {
                if (!isListening) {
                    speechRecognizer.stopListening()
                    speechRecognizer.startListening(speechIntent)
                    isListening = true
                }
            } catch (e: Exception) {
                recreateVoiceRecord()
            }

    }

    private fun recreateVoiceRecord() {
        speechRecognizer.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer.setRecognitionListener(speechListener)
        speechRecognizer.startListening(speechIntent)
        callback.onRebindToSpeechRecognitionView()
    }

    fun stopListening() {
        speechRecognizer.let {
            it.stopListening()
            it.cancel()
            speechRecognizer.destroy()
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer.setRecognitionListener(speechListener)
            callback.onRebindToSpeechRecognitionView()
        }
        isListening = false
        muteVolume(false)
        TriggerOfflineService.startService(App.get(),false)
    }

    fun destroy() {
        isListening = false
        speechRecognizer.let {
            it.stopListening();
            it.cancel();
            it.destroy();
        }
    }

    fun muteVolume(shouldMute: Boolean) {
        val alarmManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        if (alarmManager != null) {
            alarmManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, shouldMute)
            alarmManager.setStreamMute(AudioManager.STREAM_ALARM, shouldMute)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val adjustMuteOrUnMute = if (shouldMute) AudioManager.ADJUST_MUTE else AudioManager.ADJUST_UNMUTE
                alarmManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, adjustMuteOrUnMute, 0)
                alarmManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, adjustMuteOrUnMute, 0)
                alarmManager.adjustStreamVolume(AudioManager.STREAM_ALARM, adjustMuteOrUnMute, 0)
                alarmManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, adjustMuteOrUnMute, 0)
            } else {
                alarmManager.setStreamMute(AudioManager.STREAM_MUSIC, shouldMute)
            }
        }
    }
    interface SpeechRecognizerManagerCallBack {
        fun onNoNetWork()
        fun onErrorTimeOut()
        fun onRebindToSpeechRecognitionView()
    }

}