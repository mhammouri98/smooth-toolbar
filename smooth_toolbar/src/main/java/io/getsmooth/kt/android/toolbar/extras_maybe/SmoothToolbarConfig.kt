/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/
/*
* Created by Mohammed Alhammouri on 10,February,2019
* myhammouri98@gmail.com
*/package io.getsmooth.kt.android.toolbar.extras_maybe

import android.animation.TimeInterpolator
import android.graphics.Color
import android.view.animation.LinearInterpolator

object SmoothToolbarConfig {

    var DEFAULT_BACKGROUND_COLOR: Int = Color.parseColor("#ffffff")
    var DEFAULT_FOREGROUND_COLOR: Int = Color.parseColor("#f2f2f2")
    var DEFAULT_STATUS_COLOR: Int = Color.parseColor("#000000")
    val DEFAULT_WIDTH: Float = 0f
    val DEFAULT_HEIGHT: Float = 0f
    val DEFAULT_TITLE: String = ""
    val DEFAULT_DURATION: Long = 500
    val DEFAULT_ANIMATION_INTERPOLATOR: TimeInterpolator = LinearInterpolator()

}

