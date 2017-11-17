package br.com.appinfra.appinfra.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.models.FirebaseServices.ConfiguracaoFirebase
import br.com.appinfra.appinfra.models.beans.Complaints
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_insert_description.*
import java.lang.Exception

class InsertDescriptionActivity : AppCompatActivity() {

    private var firebase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_description)

        btSendComplaint.setOnClickListener {
            var complaint = Complaints()
            complaint!!.bairro = "Bairro"
            complaint!!.cidade = "Cidade"
            complaint!!.descricao = etDescription.text.toString()
            complaint!!.endereco = "Endereco"
            complaint!!.imagem = 10
            complaint!!.status = true
            complaint!!.titulo = etTitle.text.toString()

            saveComplaint(complaint)
        }

    }

    fun saveComplaint(complaint: Complaints?): Boolean {
        try {
            firebase = ConfiguracaoFirebase.getFirebase()!!.child("list_complaints")
            firebase!!.child(complaint!!.titulo).setValue(complaint)
            Toast.makeText(this, "Reclamação inserida com sucesso", Toast.LENGTH_LONG).show()
            activityPrincipal()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    fun activityPrincipal(){
        val changePage = Intent(this@InsertDescriptionActivity, PrincipalActivity::class.java)
        startActivity(changePage)
    }

}
