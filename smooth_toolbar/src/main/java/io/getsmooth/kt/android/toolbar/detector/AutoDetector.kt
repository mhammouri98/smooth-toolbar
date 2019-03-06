/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 05,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.detector

import android.graphics.drawable.Drawable
import android.view.View
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapper

interface AutoDetector {

    interface DetectionListener {
        fun onDetectionFinished(
            backgroundColor: Int?,
            backgroundDrawable: Drawable?,
            foregroundColor: Int?,
            statusBarColor: Int?,
            width: Float?,
            height: Float?,
            title: String?
        )
    }

    fun detectConfiguration(
        toolbarWrapper: ToolbarWrapper,
        rootView: View?,
        screenWidth: Int,
        screenHeight: Int,
        listener: DetectionListener?
    )


}