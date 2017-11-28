package br.com.appinfra.appinfra.models.Util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by alber on 28/11/2017.
 */
object CheckConn {

    // Check Connection
    fun verifyConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.getActiveNetworkInfo()
        return if (netInfo != null && netInfo!!.isConnected()) {
            true
        } else {
            false
        }
    }

}