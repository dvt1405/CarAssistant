package com.vnest.txznet.sdk;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import java.util.Iterator;
import java.util.LinkedList;

public class TxzWakeUpAidlManager extends BaseWakeupManager {
    private static final String ACTION_SERVICE_AIDL = "com.txznet.smartwakeup.service.TXZService";
    private static final String TAG = "TxzWakeUpAidlManager";
    private static TxzWakeUpAidlManager sInstance = new TxzWakeUpAidlManager();
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            StringBuilder sb = new StringBuilder();
            sb.append("Connect to ");
            sb.append(componentName.getPackageName());
            Log.d(TxzWakeUpAidlManager.TAG, sb.toString());
            TxzWakeUpAidlManager.this.mService = new Messenger(iBinder);
            TxzWakeUpAidlManager.this.procMsgQueue();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            StringBuilder sb = new StringBuilder();
            sb.append("disconnect to ");
            sb.append(componentName.getPackageName());
            Log.d(TxzWakeUpAidlManager.TAG, sb.toString());
            TxzWakeUpAidlManager.this.mService = null;
            TxzWakeUpAidlManager.this.onReconnectTXZ();
        }
    };
    private Handler mHandler;
    private Messenger mMessenger;
    private LinkedList<Message> mMsgQueue = new LinkedList<>();
    /* access modifiers changed from: private */
    public Messenger mService = null;
    private HandlerThread mWorkThread;

    public void initializeEnv() {
    }

    public static TxzWakeUpAidlManager getInstance() {
        return sInstance;
    }

    private TxzWakeUpAidlManager() {
        HandlerThread handlerThread = new HandlerThread("TxzProxy");
        this.mWorkThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mWorkThread.getLooper()) {
            public void handleMessage(Message message) {
                TxzWakeUpAidlManager.this.handleMsg(message);
            }
        };
        this.mMessenger = new Messenger(this.mHandler);
    }

    /* access modifiers changed from: private */
    public void handleMsg(Message message) {
        Bundle data = message.getData();
        onEvent(data.getInt(Constants.KEY_CALLBACK, 0), data);
    }

    public void transUIEvent(String str, byte[] bArr) {
        Log.d("TXZ", "transUIEvent");
        Bundle bundle = new Bundle();
        bundle.putInt("command", 6);
        bundle.putString(Constants.KEY_STR_EVENT, str);
        bundle.putByteArray(Constants.KEY_BYTES_DATA, bArr);
        sendBroadcast(bundle);
    }

    @SuppressLint("WrongConstant")
    private void bindService() {
        if (this.mContext == null) {
            Log.e("TXZ", "no invoke init, Context == null");
            return;
        }
        try {
            Intent intent = new Intent(ACTION_SERVICE_AIDL);
            intent.setPackage("com.txznet.txz");
            this.mContext.startService(intent);
            this.mContext.bindService(intent, this.mConnection, 65);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBroadcast(Bundle bundle) {
        sendMsg(9527, bundle);
    }

    private void sendMsg(int i, Bundle bundle) {
        Message obtain = Message.obtain();
        obtain.replyTo = this.mMessenger;
        obtain.what = i;
        obtain.setData(bundle);
        synchronized (this.mMsgQueue) {
            this.mMsgQueue.add(obtain);
        }
        procMsgQueue();
    }

    public void procMsgQueue() {
        if (this.mService != null) {
            synchronized (this.mMsgQueue) {
                Iterator it = this.mMsgQueue.iterator();
                while (it.hasNext()) {
                    try {
                        this.mService.send((Message) it.next());
                    } catch (RemoteException e) {
                        String str = "TXZ";
                        StringBuilder sb = new StringBuilder();
                        sb.append("send message error ");
                        sb.append(e.toString());
                        Log.e(str, sb.toString());
                    }
                }
                this.mMsgQueue.clear();
            }
            return;
        }
        bindService();
    }
}
