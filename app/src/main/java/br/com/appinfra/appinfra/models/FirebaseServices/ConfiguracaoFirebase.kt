package br.com.appinfra.appinfra.models.FirebaseServices

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by alber on 16/11/2017.
 */

// Class Firebase - Get References and Intance
class ConfiguracaoFirebase {

    companion object {

        var refenciaFirebase: DatabaseReference? = null
        var autenticacao: FirebaseAuth? = null;

        fun getFirebase(): DatabaseReference? {
            if (refenciaFirebase == null) {
                refenciaFirebase = FirebaseDatabase.getInstance().getReference();
            }
            return refenciaFirebase;
        }

        fun getFirebaseAutenticacao(): FirebaseAuth? {
            if (autenticacao == null) {
                autenticacao = FirebaseAuth.getInstance()
            }
            return autenticacao
        }
    }

}
