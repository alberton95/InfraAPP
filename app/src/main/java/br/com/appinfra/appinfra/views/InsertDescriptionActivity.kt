package br.com.appinfra.appinfra.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.models.FirebaseServices.ConfiguracaoFirebase
import br.com.appinfra.appinfra.models.beans.Complaint
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_insert_description.*
import java.lang.Exception

class InsertDescriptionActivity : AppCompatActivity() {

    private var firebase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_description)

        sendDescription.setOnClickListener {

            if(etTitle.text.isNotEmpty() && etDescription.text.isNotEmpty()){
                var title = etTitle.text.toString()
                var description = etDescription.text.toString()

                val it = Intent(this, InsertLocationActivity::class.java)
                it.putExtra("title", title)
                it.putExtra("description", description)
                startActivity(it)
            }else{
                Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_LONG).show()
            }

        }

    }

    fun saveComplaint(complaint: Complaint?): Boolean {
        try {
            firebase = ConfiguracaoFirebase.getFirebase()!!.child("list_complaints")
            firebase!!.child(complaint!!.title).setValue(complaint)
            Toast.makeText(this, "Reclamação inserida com sucesso", Toast.LENGTH_LONG).show()
            // activityPrincipal()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

}
