package com.example.bithomeassignment.repository

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//object which  handling the shared preferences used by settings repository
object AppSettingsHelper {
    private val TAG = this::class.java.simpleName

    private val PREFS_NAME = "${AppSettingsHelper::class.simpleName.toString()}.app_prefs"
    private val PREF_IS_DATA_RETRIEVED = AppSettingsHelper::class.java.simpleName + ".is_data_retrieved"
    private val PREF_IS_HAVE_INTERNET = AppSettingsHelper::class.java.simpleName + ".is_have_internet"

    /**
     * Defaults
     */
    private const val IS_DATA_RETRIEVED_DEFAULT = false
    private const val IS_HAVE_INTERNET_DEFAULT = false

    fun getSharedPref(context: Context): SharedPreferences? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isDataRetrieved(prefs: SharedPreferences): Boolean {
        return prefs.getBoolean(PREF_IS_DATA_RETRIEVED, IS_DATA_RETRIEVED_DEFAULT)
    }

    fun setIsDataRetrieved(prefs: SharedPreferences, isRetrieved: Boolean) {
        prefs.edit().putBoolean(PREF_IS_DATA_RETRIEVED, isRetrieved).apply()
    }



}