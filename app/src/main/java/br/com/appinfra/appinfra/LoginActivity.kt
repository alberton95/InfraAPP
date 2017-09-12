package br.com.appinfra.appinfra

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    // Método de passar tela Loguin para a tela Principal
    fun entrar (view: View) {
        val changePage = Intent(this, PrincipalActivity::class.java)
        startActivity(changePage)
    }

}