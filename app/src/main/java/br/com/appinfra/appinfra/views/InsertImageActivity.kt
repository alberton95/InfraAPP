package br.com.appinfra.appinfra.views

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.appinfra.appinfra.R
import kotlinx.android.synthetic.main.activity_insert_image.*

class InsertImageActivity : AppCompatActivity() {

    val TIRAR_FOTO = 1
    private lateinit  var bitmap : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_image)
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

    fun nextStep(v: View){
        val changePage = Intent(this, InsertLocationActivity::class.java)
        startActivity(changePage)
    }

}
