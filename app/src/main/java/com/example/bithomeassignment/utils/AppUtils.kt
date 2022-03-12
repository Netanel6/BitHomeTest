package com.example.bithomeassignment.utils

import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bithomeassignment.MainActivity
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

    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0F) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    // slide the view from its current position to below itself
    fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            0F,  // fromYDelta
            view.height.toFloat()) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    fun loadImage(activity: MainActivity, imagePath: String, holder: ImageView) {
        Glide.with(activity).load(imagePath)
            .diskCacheStrategy(
                DiskCacheStrategy.ALL).into(holder)
    }

    fun formatString(data: String) :String{
        var formattedString = data
        formattedString = String.format("%s/10", data)
        return formattedString
    }
}