package br.com.appinfra.appinfra.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.models.models.beans.Location.CurrentLatLong
import kotlinx.android.synthetic.main.activity_insert_location.*

class InsertLocationActivity : AppCompatActivity() {

    private var currentlocation: CurrentLatLong?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_location)
    }

    fun getLocation(v: View){
        currentlocation= CurrentLatLong()
        var status:Int? =currentlocation?.currentlatlong(this)

        if(status==1){
            tvLocationCurrent.text = "Localização atual:"
            tvResultLatLong?.text = "Latitude: "+currentlocation?.currentLat + "\n" + "Longitude: " +currentlocation?.currentLong
        }else{
            Toast.makeText(this, "Localização não obtida! Verifique sua conexão e GPS.", Toast.LENGTH_LONG).show()
        }
    }



}
