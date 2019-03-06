/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 06,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.wrapper.animation

import android.animation.TimeInterpolator
import android.view.animation.AccelerateDecelerateInterpolator
import io.getsmooth.kt.android.toolbar.extras_maybe.SmoothToolbarConfig

data class AnimationConfiguration(
    var duration: Long = SmoothToolbarConfig.DEFAULT_DURATION,
    var animationInterpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    val animateBackground: Boolean = true,
    val animateForeground: Boolean = true,
    val animateStatusBar: Boolean = true,
    val animateItems: Boolean = true,
    val animateSize: Boolean = true,
    val animateTitle: Boolean = true,
    val animateViews: Boolean = true
)