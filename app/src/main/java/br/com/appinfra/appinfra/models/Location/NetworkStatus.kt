package br.com.appinfra.appinfra.models.models.beans.Location

import android.content.Context
import android.net.ConnectivityManager

object NetWorkStatus {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }
}