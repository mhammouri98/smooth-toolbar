/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 04,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.animation.defaults

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.view.View
import androidx.appcompat.widget.Toolbar
import io.getsmooth.kt.android.toolbar.animation.ForegroundAnimation
import io.getsmooth.kt.android.toolbar.extras_maybe.SmoothToolbarConfig
import io.getsmooth.kt.android.toolbar.utils.ToolbarUtils

class DefaultForegroundAnimation : ForegroundAnimation {

    val TAG = "ForegroundAnimation"

    override fun getForegroundAnimation(
        activity: Activity?,
        oldToolbar: Toolbar?,
        newToolbar: Toolbar?,
        oldViews: List<View>?,
        newViews: List<View>?,
        oldColor: Int?,
        newColor: Int?
    ): Animator? {

        if (newColor == null) return null

        if (oldViews == null) {
            if (activity != null && oldToolbar != null)
                ToolbarUtils.colorizeToolbar(activity, oldToolbar, newViews, newColor)

            val fadeIn: Animator = ValueAnimator.ofFloat(1f, 0f).apply {
                addUpdateListener { animation ->
                    newViews?.forEach {
                        it.alpha = animation.animatedValue as Float
                    }
                }
            }
            return fadeIn
        } else {
            val colorAnimator: ValueAnimator = ValueAnimator.ofObject(
                ArgbEvaluator(),
                oldColor ?: SmoothToolbarConfig.DEFAULT_FOREGROUND_COLOR, newColor
            ).apply {
                addUpdateListener { animation ->
                    val color = animation.animatedValue as Int
                    if (activity != null && oldToolbar != null) {
                        ToolbarUtils.colorizeToolbar(activity, oldToolbar, oldViews, color)
                    }
                }
            }

            return colorAnimator
        }
    }
}