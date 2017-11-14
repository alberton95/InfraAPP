package br.com.appinfra.appinfra.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.appinfra.appinfra.R

class InsertDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_description)
    }

    fun sendComplaint(v: View){
        val changePage = Intent(this@InsertDescriptionActivity, PrincipalActivity::class.java)
        startActivity(changePage)
    }

}
