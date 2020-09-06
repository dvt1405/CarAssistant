package kun.ktupdatelibrary;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends IntentService {
    private static final int BUFFER_SIZE = 10 * 1024;
    private static final String TAG = "DownloadService";

    public DownloadService() {
        super("DownloadService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(this);
        sendBroadCastAction(DownLoadBroadCast.ACTION_WAITING, null);
        String urlStr = intent.getStringExtra(UpdateConstants.APK_DOWNLOAD_URL);
        InputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(false);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate,identity");
//            urlConnection.setRequestProperty("content-encoding", "gzip, deflate,apk");
            urlConnection.connect();
            long bytesum = 0;
            int byteread = 0;
            long bytetotal = urlConnection.getContentLengthLong();
            in = urlConnection.getInputStream();
            File dir = StorageUtils.getCacheDirectory(this);
            String apkName = UpdateConstants.APK_NAME;
            File apkFile = new File(dir, apkName);
            StorageUtils.deleteDir(apkFile);
            out = new FileOutputStream(apkFile);
            byte[] buffer = new byte[BUFFER_SIZE];
            int oldProgress = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);
                int progress = (int) (bytesum * 100L / bytetotal);
                if (progress != oldProgress) {
                    notificationHelper.updateProgress(progress);
                    sendBroadCastAction(DownLoadBroadCast.ACTION_SEND_PROGRESS, progress);
                }
                oldProgress = progress;
            }
            sendBroadCastAction(DownLoadBroadCast.ACTION_FINISH, null);
            ApkUtils.installAPk(this, apkFile);
            notificationHelper.cancel();
        } catch (Exception e) {
            Log.e(TAG,"Exception"+e.getClass().getName());
            Log.e(TAG, "download apk file error:" + e.getMessage());
            sendBroadCastAction(DownLoadBroadCast.ACTION_RETRY,null);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                    Log.e(TAG,ignored.getMessage(),ignored);

                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                    Log.e(TAG,ignored.getMessage(),ignored);
                }
            }
        }
    }

    private void sendBroadCastAction(String action, @Nullable Integer progress) {
        Intent broadCastIntent = new Intent();
        broadCastIntent.setAction(action);
        if (progress != null) {
            broadCastIntent.putExtra(DownLoadBroadCast.EXTRA_PROGRESS, progress);
        }
        sendBroadcast(broadCastIntent);
    }

}
