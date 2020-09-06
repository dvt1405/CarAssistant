package ai.kitt.vnest.feature.screensettings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    val settingLiveData by lazy { MutableLiveData<Boolean>() }
    fun update(boolean: Boolean) {
        settingLiveData.postValue(boolean)
    }
}