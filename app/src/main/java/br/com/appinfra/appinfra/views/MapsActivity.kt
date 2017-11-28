package br.com.appinfra.appinfra.views

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.appinfra.appinfra.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private val UPDATE_INTERVAL = (10 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 20000000 /* 2 sec */
    private lateinit var mMap: GoogleMap
    private lateinit var latlng: LatLng
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    lateinit var progress: ProgressDialog
    var address: String = ""
    var city: String = ""
    var neighborhood: String = ""
    var state: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        // Method Send Location
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

    // Get Location
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
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult) {
                onLocationChanged(locationResult.getLastLocation())
            }
        },

                Looper.myLooper())
    }

    // Get Location Changed
    fun onLocationChanged(location: Location) {

        try{
            val geocoder: Geocoder
            val addresses: List<Address>
            geocoder = Geocoder(this, Locale.getDefault())

            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            latitude = location.latitude
            longitude = location.longitude

            // Get Street name, city and state
            address = addresses[0].thoroughfare
            city = addresses[0].locality
            neighborhood = addresses[0].subLocality
            state = addresses[0].adminArea

            etStreetName.setText(address)
            etCity.setText(city)
            etState.setText(state)
            etNeighborhood.setText(neighborhood)

            val locationMap = LatLng(latitude,longitude)
            mMap.addMarker(createMarkers(locationMap, address, city))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMap, 18.5f))

        }catch (e: Exception){
            Toast.makeText(this, "Localização não obtida! Verifique sua conexão e GPS!", Toast.LENGTH_LONG).show()
        }

    }

    // Function map Ready
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(this@MapsActivity)

        val jampa = LatLng(-7.1588384,-34.8576049)

        mMap.addMarker(createMarkers(jampa, "My Jampa", "A wonderfull city"))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jampa, 18.5f))
    }

    // Create Markers in Map
    fun createMarkers(latLng: LatLng, title:String, snippet:String): MarkerOptions{
        return MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet)
        //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
    }

    // Method Click in Map
    override fun onMapClick(latlng:LatLng) {
        this.latlng = latlng

        progress = ProgressDialog(this)
        progress.setTitle("Carregando endereço ...")
        progress.show();

        ReserveLatLngToAddress().execute()
    }

    inner class ReserveLatLngToAddress : AsyncTask<Void, Void, Address>(){

        override fun doInBackground(vararg p0: Void?): Address? {
            try {

                var geo = Geocoder(this@MapsActivity)
                var addresses = geo.getFromLocation(this@MapsActivity.latlng.latitude
                        ,this@MapsActivity.latlng.longitude
                        , 1)

                return addresses.get(0)

            }catch (e:Exception){
                return null
            }
        }

        override fun onPostExecute(result: Address?) {

            this@MapsActivity.progress.dismiss()

            if(result != null){
                this@MapsActivity.mMap.addMarker(
                        this@MapsActivity.createMarkers(
                                this@MapsActivity.latlng,
                                result.thoroughfare,
                                result.locality + " - " + result.postalCode
                        )
                )
            }
        }
    }

    inner class ReserveAddressToLatLng : AsyncTask<Void, Void, Address>(){

        override fun doInBackground(vararg p0: Void?): Address? {
            try {

                val geocoder: Geocoder
                val addresses: List<Address>
                geocoder = Geocoder(this@MapsActivity , Locale.getDefault())
                addresses = geocoder.getFromLocation(latitude, longitude, 1)
                return addresses.get(0)
            }catch (e:Exception){
                return null
            }
        }

        override fun onPostExecute(result: Address?) {

            this@MapsActivity.progress.dismiss()

            if(result != null){
                var latlng = LatLng(result.latitude, result.longitude)
                this@MapsActivity.mMap.addMarker(
                        this@MapsActivity.createMarkers(
                                latlng,
                                result.thoroughfare,
                                result.locality + " - " + result.postalCode
                        )
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18.5f))
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

}
