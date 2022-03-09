package com.example.bithomeassignment.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Netanel Amar on 09/03/2022.
 * NetanelCA2@gmail.com
 */
object AppUtils {

    fun getDate(): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis
        return formatter.format(calendar.time)
    }
}