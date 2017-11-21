package br.com.appinfra.appinfra.views

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.models.FirebaseServices.FirebaseHelper
import br.com.appinfra.appinfra.models.beans.Complaint
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_insert_image.*
import java.util.*

class InsertImageActivity : AppCompatActivity() {

    val CAMERA = 1
    val GALLERY = 2
    private val mImageUri: Uri? = null
    var downloadUrl: String = ""
    private lateinit var bitmap: Bitmap
    lateinit var helper: FirebaseHelper
    internal var storage: FirebaseStorage? = null
    private val mProgress: ProgressDialog? = null
    lateinit var mDatabase: DatabaseReference
    internal var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_image)

        // Init Firebase
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        mDatabase = FirebaseDatabase.getInstance().reference
        helper = FirebaseHelper(mDatabase)

        btSendComplaint.setOnClickListener {

            val it = intent
            val title = it.getStringExtra("title")
            val description = it.getStringExtra("description")
            val city = it.getStringExtra("city")
            val state = it.getStringExtra("state")
            val streetName = it.getStringExtra("streetName")
            val neighborhood = it.getStringExtra("neighborhood")

            // Set Data
            val s = Complaint()
            s.title = title
            s.description = description
            s.city = city
            s.neighborhood = neighborhood
            s.adress = streetName
            s.status = "true"
            s.image = downloadUrl

            startActivity(it)

            // Save Data
            if (s != null) {
                if (helper.save(s)!!) {
                    val changePage = Intent(this, PrincipalActivity::class.java)
                    startActivity(changePage)
                    Toast.makeText(this, "Reclamação enviada com sucesso!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_SHORT).show()
            }

        }

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
                var bitmapToUri: Uri = Uri.parse(bitmap.toString())
                uploadImage(bitmapToUri)
            }
        } else if (requestCode == GALLERY && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val filePath = data.data
            uploadImage(filePath)
            Glide.with(this).load(Uri.parse(filePath.toString())).into(ivResult);
        } else {
            Toast.makeText(baseContext, "A captura foi cancelada",
                    Toast.LENGTH_SHORT).show()
        }
    }

//    fun convertToBitmap(inContext: Context, inImage: Bitmap): Uri {
//        val bytes = ByteArrayOutputStream()
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
//        var filePath = Uri.parse(path)
//      uploadImage(filePath)
//        return filePath
//    }

    private fun uploadImage(filePath: Uri) {

        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Carregando imagem...")
            progressDialog.show()

            val imageRef = storageReference!!.child("images/" + UUID.randomUUID().toString())
            imageRef.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Imagem não enviada!", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Progresso: " + progress.toInt() + " %...")
                        progressDialog.setCancelable(false)
                        progressDialog.setCanceledOnTouchOutside(false)
                        downloadUrl = taskSnapshot.downloadUrl.toString()
                    }

        } else {
            Toast.makeText(this, "Imagem não encontrada!", Toast.LENGTH_LONG).show()
        }

    }

    fun sendImage(v: View) {
        val changePage = Intent(this, PrincipalActivity::class.java)
        startActivity(changePage)
    }

}