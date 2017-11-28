package br.com.appinfra.appinfra.models.models.beans.FirebaseServices

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import br.com.appinfra.appinfra.models.models.beans.Config.Config
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by alber on 20/10/2017.
 */

// Class Receiver Messages
class myFirebaseMessassingService:FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        handleNofitication(remoteMessage!!.notification.body)
    }

    private fun handleNofitication(body: String?) {
        val pushNotification = Intent(Config.STR_PUSH)
        pushNotification.putExtra("message" , body)
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification)
    }

}