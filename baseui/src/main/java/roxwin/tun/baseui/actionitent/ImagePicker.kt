package roxwin.tun.baseui.actionitent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileDescriptor
import java.lang.Exception


object ImagePicker {
    const val DEFAULT_MIN_WIDTH_QUALITY = 400
    const val TAG = "ImagePicker"
    const val TEMP_IMAGE_NAME = "tempImage"

    fun getPickImageIntent(context: Context): Intent? {
        var chooserIntent: Intent? = null
        var intentList = mutableListOf<Intent>()
        val pickIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        takePhotoIntent.putExtra("return-data", true)
//        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(context)))
        intentList = addIntentToList(context, intentList, takePhotoIntent)
        intentList = addIntentToList(context, intentList, pickIntent)
        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(intentList.removeAt(intentList.size - 1), TAG)
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray());
        }

        return chooserIntent
    }

    fun getImageFromResult(context: Context, resultCode: Int, imageReturnedIntent: Intent?): Bitmap? {
        val file = getTempFile(context)
        if (resultCode == Activity.RESULT_OK) {
            val isCamera = (imageReturnedIntent == null
                    || imageReturnedIntent.data == null
                    || imageReturnedIntent.data?.toString()?.contains(file.toString()) ?: false)

            return if (isCamera) {
                imageReturnedIntent!!.extras!!.get("data") as Bitmap?
            } else {
                getImageResize(context,imageReturnedIntent!!.data!!)
            }
        }
        return null
    }

    /**
     * Resize to avoid using too much memory loading big images (e.g.: 2560*1920)
     **/
    fun getImageResize(context: Context, selectedImage: Uri): Bitmap? {
        var bitmap: Bitmap?
        val samplesSize = intArrayOf(5, 3, 2, 1)
        var i = 0
        do {
            bitmap = decodeBitmap(context,selectedImage,samplesSize[i])
            i++
        } while (bitmap!=null && bitmap.width < DEFAULT_MIN_WIDTH_QUALITY && i < samplesSize.size)
        return bitmap
    }

    fun decodeBitmap(context: Context, selectedImage: Uri, sampleSizes: Int): Bitmap? {
        val bitmapFactoryOption = BitmapFactory.Options()
        bitmapFactoryOption.inSampleSize = sampleSizes

        var fileDescription: AssetFileDescriptor? = null
        try {
            fileDescription = context.contentResolver.openAssetFileDescriptor(selectedImage,"r")
        }catch (e: Exception) {

        }
        fileDescription?.let {
            return BitmapFactory.decodeFileDescriptor(it.fileDescriptor,null,bitmapFactoryOption)
        }
        return null
    }

    fun addIntentToList(context: Context, list: MutableList<Intent>, intent: Intent): MutableList<Intent> {
        val resInfo = context.packageManager.queryIntentActivities(intent, 0)
        for (res in resInfo) {
            val packageName = res.activityInfo.packageName
            val targetIntent = Intent(intent)
            targetIntent.`package` = packageName
            list.add(targetIntent)
        }
        return list
    }

    fun getTempFile(context: Context): File {
        return File(context.externalCacheDir, TEMP_IMAGE_NAME).apply {
            parentFile.mkdirs()
        }
    }
}