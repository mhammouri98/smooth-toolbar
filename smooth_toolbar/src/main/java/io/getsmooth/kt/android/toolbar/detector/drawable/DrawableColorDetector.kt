/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 05,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.detector.drawable

import android.graphics.drawable.Drawable

interface DrawableColorDetector {

    interface DrawableColorDetectorListener {
        fun onDrawableColorDetected(drawableColor: Int?,drawable: Drawable)
    }

    fun detectDrawableColor(drawable: Drawable, listener: DrawableColorDetectorListener)
}