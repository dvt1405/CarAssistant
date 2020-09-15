package ai.kitt.vnest.dialogflow

import ai.kitt.vnest.App
import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson

@SuppressLint("LogNotTimber")
data class DialogFlowConfig(
        val auth_provider_x509_cert_url: String,
        val auth_uri: String,
        val client_email: String,
        val client_id: String,
        val client_x509_cert_url: String,
        val private_key: String,
        val private_key_id: String,
        val project_id: String,
        val token_uri: String,
        val type: String
) { companion object {
        const val FILE_NAME = "dialogFlow.json"

        @JvmStatic
        fun getInstance(fileName: String = FILE_NAME): DialogFlowConfig? {
            try {
                App.get().assets.open(fileName).apply {
                    val jsonString = this.readBytes().toString(Charsets.UTF_8)
                    return Gson().fromJson(jsonString, DialogFlowConfig::class.java)
                }
            } catch (e: Exception) {
                Log.e("Error", e.message, e)
            }
            return null
        }
    }
}