package br.com.appinfra.appinfra.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.appinfra.appinfra.R

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    // Método de passar tela Loguin para a tela Principal
    fun login (view: View) {
        val changePage = Intent(this, LoginActivity::class.java)
        startActivity(changePage)
    }



}

