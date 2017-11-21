package br.com.appinfra.appinfra.views

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.appinfra.appinfra.R
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_insert_location.*
import java.util.*


class InsertLocationActivity : AppCompatActivity() {

    private val UPDATE_INTERVAL = (10 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 20000000 /* 2 sec */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_location)

        btSendLocation.setOnClickListener {

            if (etStreetName.text.isNotEmpty()
                    && etState.text.isNotEmpty()
                    && etCity.text.isNotEmpty()) {

                val itGet = intent
                val gettitle = itGet.getStringExtra("title")
                val getdescription = itGet.getStringExtra("description")

                var getStreetName = etStreetName.text.toString()
                var getCity = etCity.text.toString()
                var getState = etState.text.toString()
                var getNeighborhood = etNeighborhood.text.toString()

                val itSend = Intent(this, InsertImageActivity::class.java)
                val sendTitle = itSend.putExtra("title", gettitle)
                val sendDescription = itSend.putExtra("description", getdescription)
                val sendStreetName = itSend.putExtra("streetName", getStreetName)
                val sendCity = itSend.putExtra("city", getCity)
                val sendState = itSend.putExtra("state", getState)
                val sendNeighborhood = itSend.putExtra("neighborhood", getNeighborhood)
                startActivity(itSend)
            } else {
                Toast.makeText(this, "Preencha os campos de endereço!", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    protected fun getLocation(v: View) {

        // Create the location request to start receiving updates
        var mLocationRequest = LocationRequest()
        mLocationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest!!.setInterval(UPDATE_INTERVAL)
        mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult) {
                onLocationChanged(locationResult.getLastLocation())
            }
        },

                Looper.myLooper())
    }

    fun onLocationChanged(location: Location) {

        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        // Get Street name, city and state
        val address: String = addresses[0].thoroughfare
        val city: String = addresses[0].locality
        val neighborhood: String = addresses[0].subLocality
        val state: String = addresses[0].adminArea

        etStreetName.setText(address)
        etCity.setText(city)
        etState.setText(state)
        etNeighborhood.setText(neighborhood)

        // You can now create a LatLng Object for use with maps
        val latLng = LatLng(location.getLatitude(), location.getLongitude())
    }


    fun sendLocation(v: View) {
        val changePage = Intent(this@InsertLocationActivity, InsertDescriptionActivity::class.java)
//        changePage.putExtra("name", title)
//        changePage.putExtra("description", description)
        startActivity(changePage)
    }

}

