package br.com.appinfra.appinfra.models.models.beans.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.view.View

class ConnReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        var cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var netInfo = cm.activeNetworkInfo
        var view: View

        if(netInfo != null && netInfo.isConnectedOrConnecting){
            Snackbar.make(view, "Sem conexão!", Snackbar.LENGTH_LONG).setAction("Action", null).show() }
        }

    }


}

