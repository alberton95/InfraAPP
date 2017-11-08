package br.com.appinfra.appinfra.models.models.beans.util

import android.content.Context
import android.location.Criteria
import android.location.LocationManager
import br.com.appinfra.appinfra.models.models.beans.beans.Coordenadas

/**
 * Created by alber on 07/11/2017.
 */
class Functions {

    fun getLocation(context: Context): Coordenadas { //: LatLng?
        // Get the location manager
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val bestProvider = locationManager.getBestProvider(criteria, false)
        val location = locationManager.getLastKnownLocation(bestProvider)
        //val lat: Double?
        //val lon: Double?
        var coordenada = Coordenadas(0.0,0.0,0)
        try {
            coordenada.lat = location.latitude
            coordenada.long = location.longitude
            coordenada.ativo = 1
            //msg ="Lat: "+lat.toString()+" | long: "+lon.toString()
            //return LatLng(lat, lon)
        } catch (e: NullPointerException) {
            //msg ="Erro"
            e.printStackTrace()
            //return null
        }

        return coordenada
    }

}