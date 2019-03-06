/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 05,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.detector.drawable

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build

class DefaultDrawableColorDetector : DrawableColorDetector {


    override fun detectDrawableColor(
        drawable: Drawable,
        listener: DrawableColorDetector.DrawableColorDetectorListener
    ) {
        if (drawable is ColorDrawable) {
            listener.onDrawableColorDetected(
                drawable.color, drawable
            )
        }

        if (drawable is GradientDrawable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                listener.onDrawableColorDetected(
                    drawable.color?.defaultColor, drawable
                )
            }
        }

        listener.onDrawableColorDetected(null, drawable)
    }
}