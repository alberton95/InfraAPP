package br.com.appinfra.appinfra.models.FirebaseServices

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by alber on 23/11/2017.
 */

// Class Persistence in Cache Data Application
class PersistenceCache : Application() {

    // Initialize Persistence Cache Firebase
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}