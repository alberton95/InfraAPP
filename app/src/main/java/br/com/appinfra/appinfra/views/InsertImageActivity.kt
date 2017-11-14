package br.com.appinfra.appinfra.views

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.appinfra.appinfra.R
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_insert_image.*


class InsertImageActivity : AppCompatActivity() {

    val CAMERA = 1
    val GALLERY = 2
    private lateinit var bitmap: Bitmap
    private var filePath: Uri? = null
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_image)

        // Init Firebase
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

    }

    fun setBitmapImage(b: Bitmap) {
        bitmap = b
    }

    fun callCamera(v: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    fun callGallery(v: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val bundle = data.extras
                val bitmap = bundle.get("data") as Bitmap
                setBitmapImage(bitmap)
                ivResult.setImageBitmap(bitmap)
            }
        } else if (requestCode == GALLERY && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImage = data.data
            Glide.with(this).load(Uri.parse(selectedImage.toString())).into(ivResult);
        } else {
            Toast.makeText(baseContext, "A captura foi cancelada",
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun decodeImage(path: String) {
        val targetW = ivResult.getWidth()
        val targetH = ivResult.getHeight()

        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        bmOptions.inPurgeable = true
        val bitmap = BitmapFactory.decodeFile(path, bmOptions)
        ivResult.setImageBitmap(bitmap)
    }

    fun sendImage(v: View) {
        val changePage = Intent(this, InsertLocationActivity::class.java)
        startActivity(changePage)
    }

}