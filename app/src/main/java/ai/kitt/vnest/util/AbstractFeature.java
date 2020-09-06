package ai.kitt.vnest.util;

import android.content.Intent;

import androidx.room.util.StringUtil;

import com.blankj.utilcode.util.DeviceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

abstract class AbstractFeature {
    private static List<String> songs;

    private byte getCommandChecksum(byte[] paramArrayOfByte) {
        return McuUtil.getCommandChecksum(paramArrayOfByte);
    }

    static String getSongNameByDictionary(String paramString) {
        if (songs == null)
            initMusicDictionary();
        for (String str : songs) {
            if (paramString.contains(str))
                return str;
        }
        return null;
    }

    private static String getSongNameByKeyWord(String paramString) {
        return "";
    }

    static void initMusicDictionary() {

    }

    static String normalize(String paramString) {
        return "";
    }

    static String normalize(String paramString, String... paramVarArgs) {
        int i = paramVarArgs.length;
        for (byte b = 0; b < i; b++)
            paramString = paramString.replace(paramVarArgs[b], "");
        return paramString;
    }

    protected void broadcast(String paramString, int paramInt) {
//        try {
////            Intent intent = new Intent();
////            this(paramString);
////            intent.putExtra("commandCode", paramInt);
////            AutoAIApp.getAppContext().sendBroadcast(intent);
//        } catch (Exception paramString) {
//            paramString.printStackTrace();
//        }
    }

    protected String getAlbumName(String paramString) {
       return "";
    }

    protected byte[] getCommand(int paramInt, byte... paramVarArgs) {
        return McuUtil.getCommand(paramInt, paramVarArgs);
    }

    protected Intent getCommandIntent(byte[] paramArrayOfByte) {
        return McuUtil.getCommandIntent(paramArrayOfByte);
    }

    public String getSongArtist(String paramString) {
        return "";
    }

    protected String getSongTitle(String paramString) {
        String str = getSongNameByDictionary(paramString);
        return (str == null) ? getSongNameByKeyWord(paramString) : str;
    }

    protected boolean isNativeSupported() {
        return DeviceUtils.getModel().contains("AC822X");
    }

    protected void onLogCommand(String paramString1, String paramString2) {
    }
}
