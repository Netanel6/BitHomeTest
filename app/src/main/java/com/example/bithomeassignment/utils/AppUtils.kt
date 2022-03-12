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

    // slide the view from its current position to above itself
    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,
            0F,
            view.height.toFloat(),  // fromYDelta
            0F)
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    // slide the view from its current position to below itself
    fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0F,
            0F,
            0F,
            view.height.toFloat())
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    // Loading image and caches it
    fun loadImage(activity: MainActivity, imagePath: String, holder: ImageView) {
        Glide.with(activity).load(imagePath)
            .diskCacheStrategy(
                DiskCacheStrategy.AUTOMATIC).into(holder)
    }

    // Formats string to add /10 to the current data
    fun formatString(data: String) :String{
        var formattedString = data
        formattedString = String.format("%s/10", data)
        return formattedString
    }
}