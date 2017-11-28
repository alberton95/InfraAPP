package br.com.appinfra.appinfra.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import br.com.appinfra.appinfra.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        var etEmail: String = etEmail.text.toString()
        var etPassword: String = etPassword.text.toString()

        mAuth = FirebaseAuth.getInstance();

        btRegister.setOnClickListener() {
            if(!etEmail.isNotEmpty() && !etPassword.isNotEmpty()){
                createAccount(etEmail, etPassword)
            }else{
                Toast.makeText(this@CadastroActivity, "Preencha os campos!", Toast.LENGTH_LONG).show()
            }
        }


    }

    fun createAccount(email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                    } else {
                        Toast.makeText(this@CadastroActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }

    }


    // Method Login
    fun login(view: View) {
        val changePage = Intent(this, LoginActivity::class.java)
        startActivity(changePage)
    }


}

