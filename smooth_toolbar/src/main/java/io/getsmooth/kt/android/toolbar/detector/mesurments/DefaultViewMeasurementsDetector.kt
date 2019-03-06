/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 06,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.detector.mesurments

import android.view.View
import android.view.ViewTreeObserver
import io.getsmooth.kt.android.toolbar.utils.ToolbarUtils


class DefaultViewMeasurementsDetector : ViewMeasurementsDetector {
    override fun detectMeasurements(
        view: View?,
        rootView: View?,
        screenWidth: Float,
        screenHeight: Float,
        listener: ViewMeasurementsDetector.ViewMeasurementsDetectorListener
    ) {
        if (view == null) {
            listener.onMeasurementsDetected(0f, 0f)
        } else if (view.height > 0 && view.width > 0) {
            listener.onMeasurementsDetected(view.width.toFloat(), view.height.toFloat())
        } else if (view.measuredHeight > 0 && view.measuredWidth > 0) {
            listener.onMeasurementsDetected(view.measuredWidth.toFloat(), view.measuredHeight.toFloat())
        } else {
            rootView?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    listener.onMeasurementsDetected(view.measuredWidth.toFloat(), view.measuredHeight.toFloat())
                    ToolbarUtils.removeOnGlobalLayoutListener(view, this)
                }
            })
        }
    }
}
