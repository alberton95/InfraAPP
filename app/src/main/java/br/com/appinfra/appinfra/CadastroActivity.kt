package br.com.appinfra.appinfra

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    // Método de passar tela Loguin para a tela Principal
    fun cadastrar (view: View) {
        val changePage = Intent(this, PrincipalActivity::class.java)
        startActivity(changePage)
    }

    // Método de passar tela Loguin para a tela Principal
    fun login (view: View) {
        val changePage = Intent(this, LoginActivity::class.java)
        startActivity(changePage)
    }



}

