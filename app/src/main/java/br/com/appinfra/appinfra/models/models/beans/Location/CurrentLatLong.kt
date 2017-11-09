package br.com.appinfra.appinfra.models.models.beans.Location

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings

/**
 * Created by alber on 08/11/2017.
 */
class CurrentLatLong {
    private var gpsTracker: GPSTracker? = null
    var currentLat: Double? = 0.0
    var currentLong: Double? = 0.0
    internal var mcontext: Activity?=null
    internal var status = 0

    fun currentlatlong(context: Activity): Int {

        mcontext = context

        if (br.com.appinfra.appinfra.models.models.beans.Location.NetWorkStatus.isNetworkAvailable(context)) {
            Navigation(context)

        } else {
            val alert = AlertDialog.Builder(mcontext)
            alert.setIcon(android.R.drawable.ic_dialog_alert)
            alert.setTitle("")
            alert.setMessage("Por favor, ative a conexão!")
            alert.setPositiveButton("Ok") { dialog, which -> mcontext?.finish() }
            alert.show()
        }
        return status

    }


    private fun Navigation(context: Activity) {


        val builder = AlertDialog.Builder(mcontext)
        if (hasGpsOnDevice()) {
            if (isGpsEnabledOnDevice) {
                try {
                    gpsTracker = GPSTracker(context)
                    currentLat = gpsTracker!!.getLocation()!!.latitude
                    currentLong = gpsTracker!!.getLocation()!!.longitude

                    status = 1
                } catch (e: Exception) {

                }

            } else {
                showCustomDialog()
            }
        } else {
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setTitle("")
            builder.setMessage("Seu dispositivo não possui GPS.")
            builder.setPositiveButton("Ok", null)
            builder.show()
        }
    }

    fun hasGpsOnDevice(): Boolean {
        val pm = mcontext?.packageManager
        val hasGps = pm!!.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
        return hasGps
    }

    val isGpsEnabledOnDevice: Boolean
        get() {
            val mlocManager = mcontext?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isProviderEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)


            return isProviderEnabled
        }

    fun showCustomDialog() {
        val builder = AlertDialog.Builder(mcontext)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setTitle("")
        builder.setMessage("Por favor, ative o GPS.")
        builder.setCancelable(false)
        builder.setPositiveButton("Ok") { dialog, which ->
            mcontext?.startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0)

        }
        builder.show()
    }

    private fun turnGPSOn() {

        val intent = Intent("android.location.GPS_ENABLED_CHANGE")
        intent.putExtra("enabled", true)
        mcontext?.sendBroadcast(intent)
    }
}