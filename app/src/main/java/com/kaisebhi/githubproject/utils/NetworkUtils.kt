package com.kaisebhi.githubproject.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

class NetworkUtils {
    companion object {
        val TAG = "NetworkUtil.kt"
        fun getNetworkState(context: Context): Boolean {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
                val network = connectivityManager.activeNetwork
                //LinkProperties instance contains all the information about link of network
                //like dns servers, routes, ip address etc.
                //NetworkCapabilities is an class which encapsulates information about the capabilities
                //of network states you can check state of Transport of Network(Wifi, Mobile, VPN etc.)
                val networkProperties = connectivityManager.getNetworkCapabilities(network)
                val isCellular = networkProperties?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                val isWifi = networkProperties?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                Log.d(TAG, "getNetworkState: $isWifi , $isCellular , $networkProperties")
                if(networkProperties != null && isCellular != null && isWifi != null) {
                    return isCellular || isWifi
                }
            }
            return false
        }
    }
}