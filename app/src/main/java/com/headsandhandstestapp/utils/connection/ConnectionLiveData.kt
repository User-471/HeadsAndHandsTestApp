package com.headsandhandstestapp.utils.connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData
import com.headsandhandstestapp.model.ConnectionModel

class ConnectionLiveData(private val context: Context) : LiveData<ConnectionModel>() {

    val MobileData = 2
    val WifiData = 1

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.extras != null) {
                val activeNetwork = intent.extras!!.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo
                val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
                if (isConnected) {
                    when (activeNetwork.type) {
                        ConnectivityManager.TYPE_WIFI -> postValue(ConnectionModel(WifiData, true))
                        ConnectivityManager.TYPE_MOBILE -> postValue(ConnectionModel(MobileData, true))
                    }
                } else {
                    postValue(ConnectionModel(0, false))
                }
            }
        }
    }

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkReceiver)
    }
}