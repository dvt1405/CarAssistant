package ai.kitt.vnest.basedata.database.file;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtil {
    private final static String fileName = "active";
    private final static String dirName = "webvision";
    private final static String defFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName;
    private final static String TAG = "Webvision file tag";

    public static boolean checkActiveCode() {

        return true;
    }

    public static void saveActiveCode(String activeCode, String imei) {
        File dir = new File(defFilePath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e(TAG, "mkdir failed: " + fileName);
                return;
            } else {
                Log.i(TAG, "mkdir ok: " + fileName);
            }
        } else {
            Log.w(TAG, fileName + " already exists! ");
        }
//        for (String fileName : fileNames) {
//            copyFilesFromAssets(context, assetsSrcDir + "/" + fileName, sdcardDstDir + "/" + fileName, override);
//        }
    }
}
