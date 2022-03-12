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

    /**
     * Defaults
     */
    private const val IS_DATA_RETRIEVED_DEFAULT = false

    // Get shared pref instance vis SettingsRepository
    fun getSharedPref(context: Context): SharedPreferences? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // retrieve data to shared pref using SettingsRepository
    fun isDataRetrieved(prefs: SharedPreferences): Boolean {
        return prefs.getBoolean(PREF_IS_DATA_RETRIEVED, IS_DATA_RETRIEVED_DEFAULT)
    }

    // Set data to shared pref using SettingsRepository
    fun setIsDataRetrieved(prefs: SharedPreferences, isRetrieved: Boolean) {
        prefs.edit().putBoolean(PREF_IS_DATA_RETRIEVED, isRetrieved).apply()
    }
}