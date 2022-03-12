package com.example.bithomeassignment.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.bithomeassignment.repository.AppSettingsHelper.isDataRetrieved
import com.example.bithomeassignment.repository.AppSettingsHelper.setIsDataRetrieved
import com.example.bithomeassignment.utils.LoggerUtils

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
// Class to handle shared preferences all over the app
class SettingsRepository(context:Context?): ISettingsRepository {
    private val  TAG = this::class.simpleName.toString()
    private  val _sharedPref: SharedPreferences

    // Example for how to save data to shared pref via AppSettingsHelper class
    override fun setIsRetrieved(isRetrieved:Boolean) {
        setIsDataRetrieved(_sharedPref, isRetrieved)
    }
    // Example for how to retrieve data to shared pref via AppSettingsHelper class
    override fun isRetrieved():Boolean {
        return isDataRetrieved(_sharedPref)
    }

    init {
        LoggerUtils.info(TAG, "Initializing Settings repository...")
        _sharedPref = AppSettingsHelper.getSharedPref(context!!)!!
    }
}