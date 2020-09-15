package ai.kitt.vnest.base

import ai.kitt.vnest.R
import ai.kitt.vnest.util.ConfirmDialog
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("MissingPermission", "LogNotTimber")
open class LocationActivity : AppCompatActivity(), LocationListener {
    protected val locationManager: LocationManager by lazy { getSystemService(LOCATION_SERVICE) as LocationManager; }
    private var lastLocation: Location? = null
    private val counterProviderLocation: HashSet<String?> by lazy { hashSetOf<String?>() }
    var dialogRequestOpenLocation: ConfirmDialog? = null
    var latitude = 0.0
    var longitude = 0.0
    var isLocationEnabled: Boolean = false

    fun getLastLocation() {
        isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (lastLocation == null) {
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            latitude = lastLocation?.latitude ?: 0.0
            longitude = lastLocation?.longitude ?: 0.0
        }
    }


    fun requestLocationUpdate() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000L, 50f, this)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000L, 50f, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            lastLocation = location
            latitude = location.latitude
            longitude = location.longitude
            Log.e("Location", "$latitude $longitude")
        }
    }

    override fun onStatusChanged(s: String?, i: Int, bundle: Bundle?) {}

    override fun onProviderEnabled(s: String?) {
        isLocationEnabled = true
        dismissMessageNoGps()
    }

    override fun onProviderDisabled(s: String?) {
        counterProviderLocation.add(s)
        if (counterProviderLocation.size == 2) {
            isLocationEnabled = false
            buildAlertMessageNoGps()
            counterProviderLocation.clear()
        }
    }

    fun buildAlertMessageNoGps() {
        if (dialogRequestOpenLocation == null) {
            dialogRequestOpenLocation = ConfirmDialog.Builder(this, true)
                    .title(getString(R.string.alert_enable_gps_title))
                    .message(getString(R.string.alert_enable_gps_message))
                    .setOnAllowClick {
                        it.dismiss()
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }.build()
        }
        if (dialogRequestOpenLocation?.alertDialog?.isShowing == false) {
            dialogRequestOpenLocation?.show()
        }
    }

    fun dismissMessageNoGps() {
        if (dialogRequestOpenLocation != null && dialogRequestOpenLocation?.alertDialog?.isShowing == true) {
            dialogRequestOpenLocation?.dismiss()
        }
    }
}