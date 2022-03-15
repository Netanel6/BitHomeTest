package com.example.bithomeassignment.network

import com.example.bithomeassignment.utils.LoggerUtils
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Netanel Amar on 15/03/2022.
 * NetanelCA2@gmail.com
 */
object RetrofitInstance {
    private val TAG = this::class.simpleName.toString()

    fun <T> create(service: Class<T>): T {
        LoggerUtils.info(TAG, "Initializing Retrofit...")

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build()
        return retrofit.create(service)
    }
}