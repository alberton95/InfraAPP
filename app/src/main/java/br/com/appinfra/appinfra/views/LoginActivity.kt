package br.com.appinfra.appinfra.views

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.appinfra.appinfra.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val progress: ProgressDialog by lazy { ProgressDialog(this) }
    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Check Login Firebase
        checkloguin()

        // Action Button Send Loguin
        btEnviar.setOnClickListener {view ->
            signIn(view,etUsuario.text.toString(), etSenha.text.toString())
        }
    }

    // Method Transition Activity - Login > Register
    fun activityRegister (v: View) {
        val changePage = Intent(this, CadastroActivity::class.java)
        startActivity(changePage)
    }

    // Method Transition Activity - Login > Main
    fun activityIndex () {
        val changePage = Intent(this, PrincipalActivity::class.java)
        startActivity(changePage)
    }

    // Method Check Loguin in Firebase and Shared Preferences
    fun checkloguin(){
        FirebaseAuth.getInstance()
        // other setup code
        val mPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (mPrefs.getBoolean("conectado", false)) {
            val i = Intent(this, PrincipalActivity::class.java)
            startActivity(i)
            finish()
        } else {
        }
    }

    // Method Enter
    fun signIn(view: View,usuario: String, senha: String){

        if(etUsuario.text.toString().isNotEmpty() && etSenha.text.toString().isNotEmpty()){
            fbAuth.signInWithEmailAndPassword(usuario, senha).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                if(task.isSuccessful){
                    progress.setCancelable(false)
                    progress.setMessage("Entrando ...")
                    progress.show()
                    sharedPref()
                    activityIndex()
                }else{
                    showMessage(view,"E-mail ou senha inválidos!")
                }
            })
        }else{
            showMessage(view,"Preencha os campos!")
        }

    }

    // Method Checkbox Keep Connected
    fun sharedPref(){

        if(cbManterConectado.isChecked == true){
            var pref = PreferenceManager.getDefaultSharedPreferences(this)
            var editor = pref.edit()

            //save infos
            editor.putBoolean("conectado", true)
            editor.commit()
        }else{
        }
    }

    // Show Message Error Loguin
    fun showMessage(view:View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
    }


}

