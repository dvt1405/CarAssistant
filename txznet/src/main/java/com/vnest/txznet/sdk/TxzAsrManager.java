package com.vnest.txznet.sdk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TxzAsrManager {
    private static TxzAsrManager sInstance = new TxzAsrManager();
    private IAsrRegCmdCallBack mCommCallBack = new IAsrRegCmdCallBack() {
        public void notify(String str, String str2) {
            for (CommandListener commandListener : TxzAsrManager.this.mCommandListeners) {
                if (commandListener != null) {
                    commandListener.onCommand(str, str2);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public Set<CommandListener> mCommandListeners = new HashSet();
    private Map<String, String> mMapRemoteCommands = new HashMap();
    private Map<String, String> mMapRemoteRawTexts = new HashMap();

    public interface CommandListener {
        void onCommand(String str, String str2);
    }

    private static abstract class IAsrRegCmdCallBack {
        public abstract void notify(String str, String str2);

        private IAsrRegCmdCallBack() {
        }
    }

    public static TxzAsrManager getInstance() {
        return sInstance;
    }

    private synchronized void addCommand(String str, String str2) {
        this.mMapRemoteCommands.put(str, str2);
    }

    private synchronized void removeCommand(String str) {
        this.mMapRemoteCommands.remove(str);
    }

    public void addCommandListener(CommandListener commandListener) {
        this.mCommandListeners.add(commandListener);
    }

    public void regCmd(String[] strArr, String str) {
        for (String addCommand : strArr) {
            addCommand(addCommand, str);
        }
        TxzWakeUpAidlManager.getInstance().regCommand(strArr, str);
    }

    public void unregCmd(String[] strArr) {
        for (String str : strArr) {
            removeCommand(str);
            this.mMapRemoteRawTexts.remove(str);
        }
        TxzWakeUpAidlManager.getInstance().unregCommand(strArr);
    }

    private synchronized void regCmdAgain() {
        HashMap hashMap = new HashMap();
        for (Entry entry : this.mMapRemoteCommands.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            Set set = (Set) hashMap.get(str2);
            if (set == null) {
                set = new HashSet();
                hashMap.put(str2, set);
            }
            set.add(str);
        }
        for (Object str3 : hashMap.keySet()) {
            TxzWakeUpAidlManager.getInstance().regCommand((String[]) ((Set) hashMap.get(str3)).toArray(), ((String) str3));
        }
    }

    public void onCommand(String str, String str2) {
        IAsrRegCmdCallBack iAsrRegCmdCallBack = this.mCommCallBack;
        if (iAsrRegCmdCallBack != null) {
            iAsrRegCmdCallBack.notify(str2, str);
        }
    }

    public void onReconnectTXZ() {
        regCmdAgain();
    }
}
