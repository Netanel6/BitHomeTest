package com.example.bithomeassignment.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
// Object to handle logs and toasts
object LoggerUtils {
    private val TAG = this::class.simpleName.toString()

    fun info(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun error(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    fun shortToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun snackBar(view: View, msg: String) {
        val snackbar: Snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}