package com.example.bithomeassignment

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData

// Class that handles internet connection (WIFI or 4G)
class ConnectionLiveData(context: Context) : LiveData<Boolean>() {
    private val TAG = this::class.simpleName.toString()
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()


    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }

    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        // Detects internet connection is available
        override fun onAvailable(network: Network) {
            Log.d(TAG, "onAvailable: $network")
            val networkCapabilities = cm.getNetworkCapabilities(network)
            val isInternet = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
            if (isInternet == true) {
                validNetworks.add(network)
            }
            checkValidNetworks()
        }

        // Detects internet connection is lost
        override fun onLost(network: Network) {
            Log.d(TAG, "onLost: $network")
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }

}