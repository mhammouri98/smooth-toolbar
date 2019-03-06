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

interface ViewMeasurementsDetector {

    interface ViewMeasurementsDetectorListener {
        fun onMeasurementsDetected(width: Float, height: Float)
    }

    fun detectMeasurements(
        view: View?,
        rootView: View?,
        screenWidth: Float,
        screenHeight: Float,
        listener: ViewMeasurementsDetectorListener
    )

}