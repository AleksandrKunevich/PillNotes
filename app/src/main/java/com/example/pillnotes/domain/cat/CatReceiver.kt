package com.example.pillnotes.domain.cat

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import android.widget.Toast
import com.example.pillnotes.R
import com.example.pillnotes.presentation.CatFragment

class CatReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val a = CatFragment().catViewModel
                a.getCatRandomBitmap()
                Log.e("!!!!!!!!!", "onAvailable: $a")
            }

            override fun onLost(network: Network) {
                Toast.makeText(
                    context,
                    context.getString(R.string.check_internet),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}