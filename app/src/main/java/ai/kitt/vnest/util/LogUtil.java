package ai.kitt.vnest.util;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogUtil {
    public static final String DEFAULT_WORK_SPACE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/webvisionvoice/";
    public static final String LOG_FILE_NAME = "log.txt";
    public static final String TAG = "WebVisionVoice Log";
    public static final String EXCEPTION_TAG = "Error";
    private String mFileName;
    private static LogUtil INSTANCE;

    private LogUtil() {

    }

    public static LogUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LogUtil();
        }
        return INSTANCE;
    }

    @SuppressLint("LogNotTimber")
    public static void log(String message) {
        try {
            String sdcardDstDir = DEFAULT_WORK_SPACE;
            File dir = new File(sdcardDstDir);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Log.e(TAG, "mkdir failed: " + sdcardDstDir);
                } else {
                    Log.i(TAG, "mkdir ok: " + sdcardDstDir);
                    log(message);
                }
            } else {
                Log.w(TAG, sdcardDstDir + " already exists! ");
                File file = new File(sdcardDstDir, LOG_FILE_NAME);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(message.getBytes());
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static void log(String tag, String message) {
        log(tag +":\t"+message);
    }

    public static void log(Exception ex) {
        StringBuilder message = new StringBuilder();
        message.append(ex.getMessage()).append("\n");
        StackTraceElement[] stackTraceElement = ex.getStackTrace();
        for (StackTraceElement traceElement : stackTraceElement) {
            message.append(traceElement.getFileName())
                    .append(" ")
                    .append(traceElement.getClassName())
                    .append(" ")
                    .append(traceElement.getMethodName())
                    .append(" ")
                    .append(traceElement.getLineNumber())
                    .append("\n");
        }
        log(message.toString());
    }

}
