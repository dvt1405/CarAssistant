package ai.kitt.vnest.speechmanager.texttospeech;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import ai.kitt.vnest.App;

public class TextToSpeechManager {
    private static final String LOG_TAG = "Text to speech";
    public static final int UPDATE_AFTER_PROCESS_TEXT = 4;
    public static final int RESTART_VOICE_RECOGNITION = 1;
    public static final int STOP_VOICE_RECOGNITION = 2;
    private static final String TEXT_TO_SPEECH_RESTART_VOICE_RECORD = "restart_voice";
    public static boolean initSuccess = false;

    public static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RESTART_VOICE_RECOGNITION:
                    mTextToSpeechListener.onRestartVoice();
                    break;
                case STOP_VOICE_RECOGNITION:
                    mTextToSpeechListener.onStopVoiceRecord();
                    break;
                case UPDATE_AFTER_PROCESS_TEXT:
                    mTextToSpeechListener.onUpdateMessageAfterProcessText(msg);
                default:
                    break;
            }
        }
    };

    private static TextToSpeechListener mTextToSpeechListener;

    private TextToSpeech mTextToSpeech;

    private static WeakReference<TextToSpeechManager> INSTANCE;

    public static TextToSpeechManager getInstance(Context context) {
        if (INSTANCE == null || INSTANCE.get() == null) {
            INSTANCE = new WeakReference<>(new TextToSpeechManager(context));
        }
        return INSTANCE.get();
    }

    public TextToSpeechManager(Context context) {
        initTextToSpeech(context);
    }

    public void initTextToSpeech(Context context) {
        mTextToSpeech = new TextToSpeech(context, status -> {
            if(status == TextToSpeech.ERROR) {
                initSuccess = false;
            } else {
                initSuccess = true;
                mTextToSpeech.setLanguage(Locale.getDefault());
            }
            mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    Log.d(LOG_TAG, "On Start");
                    android.os.Message message = mHandler.obtainMessage(STOP_VOICE_RECOGNITION);
                    message.sendToTarget();
                }

                @Override
                public void onDone(String utteranceId) {
                    Log.e(LOG_TAG, utteranceId);
                    if (utteranceId.equals(TEXT_TO_SPEECH_RESTART_VOICE_RECORD)) {
                        android.os.Message message = mHandler.obtainMessage(RESTART_VOICE_RECOGNITION);
                        message.sendToTarget();
                    }
                }

                @Override
                public void onError(String utteranceId) {
                    Log.d(LOG_TAG, "On Error");
                }
            });
        }, "com.google.android.tts");

        Set<String> a = new HashSet<>();
        a.add("male");
        Voice v = new Voice("vn-VN-x-vnm#male_2-network", Locale.getDefault(), 400, 200, true, a);
        mTextToSpeech.setVoice(v);
        mTextToSpeech.setSpeechRate(1.8f);
    }

    public void speak(String key, boolean shouldRestartRecord) {
        if(!initSuccess) {
            initTextToSpeech(App.get());
        }
        if (!shouldRestartRecord) {
            mTextToSpeech.speak(key, TextToSpeech.QUEUE_FLUSH, null);
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, TEXT_TO_SPEECH_RESTART_VOICE_RECORD);
            mTextToSpeech.speak(key, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    public void setTextToSpeechListener(TextToSpeechListener textToSpeechListener) {
        mTextToSpeechListener = textToSpeechListener;
    }
    public void shutDown() {
        mTextToSpeech.shutdown();
    }

    public interface TextToSpeechListener {
        void onRestartVoice();

        void onStopVoiceRecord();

        void onUpdateMessageAfterProcessText(Message msg);
    }

}
