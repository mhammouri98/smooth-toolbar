/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 02,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.animation.defaults

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import io.getsmooth.kt.android.toolbar.animation.StatusBarAnimation
import io.getsmooth.kt.android.toolbar.extras_maybe.SmoothToolbarConfig

class DefaultStatusBarAnimation : StatusBarAnimation {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getStatusBarAnimation(window: Window, oldColor: Int?, newColor: Int?): Animator? {

        val colorAnimator: ValueAnimator = ValueAnimator.ofObject(
            ArgbEvaluator(),
            oldColor ?: SmoothToolbarConfig.DEFAULT_STATUS_COLOR, newColor ?: SmoothToolbarConfig.DEFAULT_STATUS_COLOR
        )

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        colorAnimator.addUpdateListener { animation ->
            val color = animation.animatedValue as Int
            window.statusBarColor = color
        }

        return colorAnimator
    }

}