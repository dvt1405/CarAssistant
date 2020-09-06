package kun.ktupdatelibrary;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class DownLoadBroadCast extends BroadcastReceiver {
    public static final String ACTION_SEND_PROGRESS = "progress";
    public static final String ACTION_FINISH = "finish";
    public static final String EXTRA_PROGRESS = "extra_progress";
    public static final String ACTION_WAITING = "waiting";
    public static final String ACTION_RETRY = "retry";
    public static final String ACTION_TIMEOUT = "timeout";

    public static DownLoadBroadCast initBroadCast(Context context, OnReceive onReceive) {
        DownLoadBroadCast downLoadBroadCast = new DownLoadBroadCast(onReceive);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownLoadBroadCast.ACTION_SEND_PROGRESS);
        intentFilter.addAction(DownLoadBroadCast.ACTION_FINISH);
        intentFilter.addAction(DownLoadBroadCast.ACTION_WAITING);
        intentFilter.addAction(DownLoadBroadCast.ACTION_RETRY);
        context.registerReceiver(downLoadBroadCast, intentFilter);
        return downLoadBroadCast;
    }

    private OnReceive listener;

    public DownLoadBroadCast(OnReceive listener) {
        this.listener = listener;
    }

    @Override

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) return;
        switch (action) {
            case ACTION_SEND_PROGRESS:
                int progress = intent.getIntExtra(EXTRA_PROGRESS, 0);
                listener.onReceiveProgress(progress);
                break;
            case ACTION_FINISH:
                listener.onFinish();
                break;
            case ACTION_WAITING:
                listener.onWaiting();
                break;
            case ACTION_RETRY:
                listener.onRetry();
        }
    }

    public interface OnReceive {
        void onReceiveProgress(int progress);

        void onFinish();

        void onWaiting();

        void onRetry();
    }
}
