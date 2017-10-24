package br.com.appinfra.appinfra.models.models.beans.FirebaseServices

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by alber on 20/10/2017.
 */
class myFirebaseIdService: FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val refreshedToken = FirebaseInstanceId.getInstance().token
        sendRegistrationToServer(refreshedToken!!)
    }

    private fun sendRegistrationToServer(refreshedToken: String) {
        Log.d("DEBUG", refreshedToken)
    }

}