package ai.kitt.snowboy.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import ai.kitt.snowboy.AppResCopy;
import ai.kitt.snowboy.MsgEnum;
import ai.kitt.snowboy.R;
import ai.kitt.snowboy.audio.AudioDataSaver;
import ai.kitt.snowboy.audio.PlaybackThread;
import ai.kitt.snowboy.audio.RecordingThread;

public class TriggerOfflineService extends Service {
    public static int keyStartService;
    private RecordingThread recordingThread;
    private PlaybackThread playbackThread;
    public static boolean isServiceRunning = false;
    public final static int WAKE_UP = 0;
    public final static int TURN_ON_MIC = 1;
    public static String KEY_START = "extra_trigger";
    private static final String NOTIFICATION_CHANNEL_ID = "vnest.webvisionvoide";
    private static final String CHANNEL_NAME = "Background Service";
    public String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    public static void startService(Context context, boolean isWakeUp) {
        Intent intent = new Intent(context, TriggerOfflineService.class);
        if (isWakeUp) {
            isServiceRunning = true;
            intent.putExtra(KEY_START, WAKE_UP);
        }
        try {
            context.startService(intent);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }


    public static void stopService(Context context) {
        Intent intent = new Intent(context, TriggerOfflineService.class);
        try {
            context.stopService(intent);
            isServiceRunning = false;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void startForceGroundServiceWithNotification() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.createNotificationChannel(notificationChannel);
                Notification.Builder builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID);
                Notification notification = builder.setOngoing(true)
                        .setContentTitle("Webvision voice is using your microphone")
                        .setPriority(Notification.PRIORITY_MAX)
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .setSmallIcon(R.mipmap.ic_notification)
                        .build();
                startForeground(2, notification);
            }
        } catch (Exception ex) {

        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        initAlexaDetect();
        startForceGroundServiceWithNotification();
    }

    @SuppressLint("HandlerLeak")
    public Handler handlerHotWordDetect = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(android.os.Message msg) {
            MsgEnum message = MsgEnum.getMsgEnum(msg.what);
            switch (message) {
                case MSG_ACTIVE:
                    updateLog(" ============== Detected Offline ==============");
                    updateIfActive();
                    break;
                case MSG_INFO:
                    updateLog(" ============== " + message + "==============");
                    break;
                case MSG_VAD_SPEECH:
//                    updateLog(" ============== normal voice" + " Offline==============");
                    break;
                case MSG_VAD_NOSPEECH:
//                    updateLog(" ============== no speech" + " Offline==============");
                    break;
                case MSG_ERROR:
                    startOfflineRecording();
                    updateLog(" ============== Error " + msg.toString() + " Offline==============");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        keyStartService = intent.getIntExtra(KEY_START, TURN_ON_MIC);
        if (keyStartService == WAKE_UP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForceGroundServiceWithNotification();
            } else {
                startForeground(1, new Notification());
            }
        }
        startOfflineRecording();
        return START_NOT_STICKY;
    }

    private void initAlexaDetect() {
        AppResCopy.copyResFromAssetsToSD(this);
        recordingThread = RecordingThread.getInstance(handlerHotWordDetect, AudioDataSaver.getInstance());
        playbackThread = PlaybackThread.getInstance();
    }


    public void updateLog(final String text) {
        Log.e("Speech offline log", text);
    }

    public void startOfflineRecording() {
        if (recordingThread != null) {
            recordingThread.startRecording();
        }
    }

    public void updateIfActive() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                switch (keyStartService) {
                    case WAKE_UP:
                        intent.setAction(TriggerBroadCast.ACTION_START_APP);
                        break;
                    case TURN_ON_MIC:
                        intent.setAction(TriggerBroadCast.ACTION_TURN_MIC_ON);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + keyStartService);
                }
                sendBroadcast(intent);
            }
        }, 300);
        stopOfflineRecording();
    }

    public void stopOfflineRecording() {
        try {
            if (recordingThread != null) {
                recordingThread.stopRecording();
            }
            updateLog(" ==============> Offline recording stopped ==============");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    public static final String TAG = "VnestService";

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopOfflineRecording();
        recordingThread = null;
        playbackThread = null;
//        Intent intent = new Intent();
//        intent.setAction(TriggerBroadCast.ACTION_RESTART_SERVICE);
//        sendBroadcast(intent);
    }
}
