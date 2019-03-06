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
import io.getsmooth.kt.android.toolbar.detector.drawable.DrawableColorDetector
import io.getsmooth.kt.android.toolbar.detector.mesurments.ViewMeasurementsDetector
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapper


abstract class BaseAutoDetector : AutoDetector {

    val TAG = "AutoDetector"

    override fun detectConfiguration(
        toolbarWrapper: ToolbarWrapper,
        rootView: View?,
        screenWidth: Int,
        screenHeight: Int,
        listener: AutoDetector.DetectionListener?
    ) {

        var foregroundColor: Int? = null
        var title: String? = null
        var backgroundColor: Int? = null
        var backgroundDrawable: Drawable? = null
        var statusBarColor: Int? = null

        if (toolbarWrapper.autoDetectionConfiguration
                .detectForeground
        ) foregroundColor = getForegroundColor(toolbarWrapper)

        if (toolbarWrapper.autoDetectionConfiguration
                .detectTitle
        ) title = getTitle(toolbarWrapper)


        if (toolbarWrapper.autoDetectionConfiguration.detectBackground) {

            val views: MutableList<View> = arrayListOf()
            toolbarWrapper.appBarLayout?.also { views.add(it) }
            toolbarWrapper.collapsingToolbarLayout?.also { views.add(it) }
            toolbarWrapper.toolbar?.also { views.add(it) }
            views.addAll(toolbarWrapper.otherViews)

            val backgrounds: MutableList<Drawable> = arrayListOf()
            views.forEach {
                if (it.background != null) {
                    backgrounds.add(it.background)
                }
            }


            getBackground(toolbarWrapper,
                backgrounds,
                object : DrawableColorDetector.DrawableColorDetectorListener {
                    override fun onDrawableColorDetected(drawableColor: Int?, drawable: Drawable) {
                        backgroundColor = drawableColor
                        backgroundDrawable = drawable

                        if (toolbarWrapper.autoDetectionConfiguration.detectStatusBarColor) {
                            if (backgroundColor != null) {
                                statusBarColor = getStatusBarColor(backgroundColor!!)
                            }
                        }

                        sendResultOrGetMeasurements(
                            toolbarWrapper,
                            rootView,
                            screenWidth,
                            screenHeight,
                            backgroundColor,
                            backgroundDrawable,
                            foregroundColor,
                            statusBarColor,
                            title,
                            listener
                        )
                    }
                })
        } else {
            sendResultOrGetMeasurements(
                toolbarWrapper,
                rootView,
                screenWidth,
                screenHeight,
                backgroundColor,
                backgroundDrawable,
                foregroundColor,
                statusBarColor,
                title,
                listener
            )
        }


    }

    private fun sendResultOrGetMeasurements(
        toolbarWrapper: ToolbarWrapper,
        rootView: View?,
        screenWidth: Int,
        screenHeight: Int,
        backgroundColor: Int?,
        backgroundDrawable: Drawable?,
        foregroundColor: Int?,
        statusBarColor: Int?,
        title: String?,
        listener: AutoDetector.DetectionListener?
    ) {
        var finalWidth: Float? = null
        var finalHeight: Float? = null
        if (toolbarWrapper.autoDetectionConfiguration.detectMeasurements) {

            getMeasurements(toolbarWrapper,
                rootView!!,
                screenWidth.toFloat(),
                screenHeight.toFloat(),
                object : ViewMeasurementsDetector.ViewMeasurementsDetectorListener {
                    override fun onMeasurementsDetected(width: Float, height: Float) {
                        finalWidth = width
                        finalHeight = height
                        sendResult(
                            backgroundColor,
                            backgroundDrawable,
                            foregroundColor,
                            statusBarColor,
                            finalWidth,
                            finalHeight,
                            title,
                            listener
                        )
                    }
                })
        } else {
            sendResult(
                backgroundColor,
                backgroundDrawable,
                foregroundColor,
                statusBarColor,
                finalWidth,
                finalHeight,
                title,
                listener
            )
        }
    }

    fun sendResult(
        backgroundColor: Int?,
        backgroundDrawable: Drawable?,
        foregroundColor: Int?,
        statusBarColor: Int?,
        width: Float?,
        height: Float?,
        title: String?,
        listener: AutoDetector.DetectionListener?
    ) {
        listener?.onDetectionFinished(
            backgroundColor,
            backgroundDrawable,
            foregroundColor,
            statusBarColor,
            width,
            height,
            title
        )

    }


    abstract fun getMeasurements(
        toolbarWrapper: ToolbarWrapper,
        rootView: View, screenWidth: Float,
        screenHeight: Float,
        listener: ViewMeasurementsDetector.ViewMeasurementsDetectorListener
    )

    abstract fun getBackground(
        toolbarWrapper: ToolbarWrapper,
        backgrounds: List<Drawable>,
        listener: DrawableColorDetector.DrawableColorDetectorListener
    )

    abstract fun getStatusBarColor(toolbarColor: Int): Int?

    abstract fun getTitle(toolbarWrapper: ToolbarWrapper): String?

    abstract fun getForegroundColor(toolbarWrapper: ToolbarWrapper): Int?


}