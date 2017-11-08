package br.com.appinfra.appinfra.views

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.models.models.beans.Location.CurrentLatLong
import br.com.appinfra.appinfra.models.models.beans.beans.Coordenadas
import br.com.appinfra.appinfra.models.models.beans.util.Functions
import kotlinx.android.synthetic.main.activity_insert_data.*

class InsertDataActivity : AppCompatActivity() {

    val TIRAR_FOTO = 1
    private var msg :String = ""
    private lateinit  var bitmap : Bitmap
    private var coord = Coordenadas(0.0,0.0,0)
    private val func = Functions()
    var textView: TextView?=null
    var textView2: TextView?=null
    private var currentlocation: CurrentLatLong?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)
        Initialize()
    }


    fun Initialize(){
        textView=findViewById(R.id.tvResult) as TextView

        currentlocation= CurrentLatLong()
        var status:Int? =currentlocation?.currentlatlong(this)

        if(status==1){
            tvResult?.text = "Latitude: "+currentlocation?.currentLat + "Longitude: " +currentlocation?.currentLong
        }

    }

    fun addText(v:String){
        tvResult.setText(v)
    }

    fun setBitmapImage(b:Bitmap){
        bitmap = b
    }

    fun callCamera(v: View){
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, TIRAR_FOTO)
        } finally {
            try {

            } catch(ex: SecurityException) {
                Toast.makeText(this, "CAMERA NAO CHAMADA", Toast.LENGTH_LONG).show()
            }
        }
        addText(msg)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TIRAR_FOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val bundle = data.extras
                    val bitmap = bundle.get("data") as Bitmap
                    setBitmapImage(bitmap)
                    ivResult.setImageBitmap(bitmap)
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(baseContext, "A captura foi cancelada",
                            Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "A câmera foi fechada",
                            Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }else{
                Toast.makeText(baseContext, "A captura foi cancelada",
                        Toast.LENGTH_SHORT).show()

            }
        }else{
            onBackPressed()
        }
    }

}
