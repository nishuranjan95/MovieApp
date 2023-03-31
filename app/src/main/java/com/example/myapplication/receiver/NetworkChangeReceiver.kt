package com.example.myapplication.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.example.myapplication.network.Network

class NetworkChangeReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            if (intent.action== ConnectivityManager.CONNECTIVITY_ACTION) {
                if(Network.isConnected(context)) {
                    val apiCallIntent = Intent("com.example.myapplication.API_CALL_INTENT")
                    context?.sendBroadcast(apiCallIntent)
//                    val homeIntent = Intent("com.example.myapplication.NETWORK")
//                    context?.sendBroadcast(homeIntent)
                    Toast.makeText(context,"network connected",Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(context,"network disconnected",Toast.LENGTH_SHORT).show()
            }
        }

    }

}