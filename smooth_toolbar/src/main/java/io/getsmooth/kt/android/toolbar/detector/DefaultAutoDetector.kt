/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 06,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.detector

import android.graphics.drawable.Drawable
import android.view.View
import io.getsmooth.kt.android.toolbar.detector.drawable.DefaultDrawableColorDetector
import io.getsmooth.kt.android.toolbar.detector.drawable.DrawableColorDetector
import io.getsmooth.kt.android.toolbar.detector.mesurments.DefaultViewMeasurementsDetector
import io.getsmooth.kt.android.toolbar.detector.mesurments.ViewMeasurementsDetector
import io.getsmooth.kt.android.toolbar.extras_maybe.SmoothToolbarConfig
import io.getsmooth.kt.android.toolbar.utils.ToolbarUtils
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapper

class DefaultAutoDetector(
    protected val drawableColorDetector: DrawableColorDetector = DefaultDrawableColorDetector(),
    protected val viewMeasurementsDetector: ViewMeasurementsDetector = DefaultViewMeasurementsDetector()
) : BaseAutoDetector() {

    override fun getBackground(
        toolbarWrapper: ToolbarWrapper,
        backgrounds: List<Drawable>,
        listener: DrawableColorDetector.DrawableColorDetectorListener
    ) {

        var backgroundDetected = false

        for ((i, drawable) in backgrounds.withIndex()) {
            drawableColorDetector.detectDrawableColor(drawable, object :
                DrawableColorDetector.DrawableColorDetectorListener {
                override fun onDrawableColorDetected(drawableColor: Int?, drawable: Drawable) {
                    if (backgroundDetected || i == backgrounds.size) return
                    if (i < backgrounds.size - 1) {
                        if (drawableColor == null) return
                        listener.onDrawableColorDetected(drawableColor, drawable)
                        backgroundDetected = true
                    } else {
                        if (!backgroundDetected) {
                            listener.onDrawableColorDetected(
                                drawableColor ?: SmoothToolbarConfig.DEFAULT_BACKGROUND_COLOR
                                , drawable
                            )
                        }
                    }
                }
            })
        }

    }

    override fun getMeasurements(
        toolbarWrapper: ToolbarWrapper,
        rootView: View, screenWidth: Float,
        screenHeight: Float,
        listener: ViewMeasurementsDetector.ViewMeasurementsDetectorListener
    ) {
        if (toolbarWrapper.layout != null) {
            viewMeasurementsDetector.detectMeasurements(
                toolbarWrapper.layout,
                rootView,
                screenWidth,
                screenHeight,
                object : ViewMeasurementsDetector.ViewMeasurementsDetectorListener {
                    override fun onMeasurementsDetected(width: Float, height: Float) {
                        listener.onMeasurementsDetected(width, height)
                    }
                }
            )
        }

    }

    override fun getForegroundColor(toolbarWrapper: ToolbarWrapper): Int? =
        toolbarWrapper.titleTv?.currentTextColor

    override fun getTitle(toolbarWrapper: ToolbarWrapper): String = toolbarWrapper.titleTv?.text.toString()

    override fun getStatusBarColor(toolbarColor: Int): Int = ToolbarUtils.getDarkenColor(toolbarColor)

}