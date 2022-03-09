package com.example.bithomeassignment.repository

import android.content.SharedPreferences

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
//Interface that implemented in the settings repository
interface ISettingsRepository {
    fun isRetrieved(): Boolean
    fun setIsRetrieved(isRetrieved:Boolean)
}