/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/
/*
* Created by Mohammed Alhammouri on 08,February,2019
* myhammouri98@gmail.com
*/
package io.getsmooth.kt.android.toolbar.animation.defaults

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.core.animation.doOnEnd
import io.getsmooth.kt.android.toolbar.animation.BackgroundAnimation


class DefaultBackgroundAnimation : BackgroundAnimation {

    val TAG = "backgroundAnimation"

    override fun getBackgroundAnimation(
        oldView: View?,
        newView: View?,
        oldBackgroundColor: Int?,
        newBackgroundColor: Int?,
        oldDrawable: Drawable?,
        newDrawable: Drawable?
    ): Animator? {
        var finalOldBackgroundColor: Int? = null
        if (oldView != null) {
            finalOldBackgroundColor = oldBackgroundColor
                ?: throw IllegalArgumentException("Old Toolbar must have either background drawable or background color")
        }

        val finalNewBackgroundColor: Int? = newBackgroundColor
            ?: throw IllegalArgumentException("New Toolbar must have either background drawable or background color")

        if (oldView != null) {
            //THERE IS A PREVIOUS VIEW
            //WE MUST HIDE IT OR TRANSITION BETWEEN THE FIRST AND THE SECOND ONE

            if (newView == null) {
                //No new toolbar fadeout the current one or just change size so don't do something  here
//                val fadeOut: Animator = ValueAnimator.ofFloat(1f, 0f).apply {
//                    addUpdateListener { animation ->
//                        oldView?.alpha = animation.animatedValue as Float
//                    }
//                }
//                return fadeOut
                return null
            }

            if (oldDrawable is ColorDrawable &&
                newDrawable is ColorDrawable
            ) {
                Log.d(TAG, "Transitioning based on color drawable")
                //Just transition colors
                val colorAnimator: ValueAnimator = ValueAnimator.ofObject(
                    ArgbEvaluator(),
                    finalOldBackgroundColor,
                    finalNewBackgroundColor
                )

                colorAnimator.addUpdateListener { animation ->
                    val color = animation.animatedValue as Int
                    oldView.setBackgroundColor(color)
                }

                return colorAnimator
            }

            if (oldDrawable is ColorDrawable &&
                newDrawable !is ColorDrawable
            ) {
                //change to new background color then fade the new drawable in
                Log.d(TAG, "Transitioning based on color to non color")

                val colorAnimator: ValueAnimator = ValueAnimator.ofObject(
                    ArgbEvaluator(),
                    finalOldBackgroundColor,
                    finalNewBackgroundColor
                )

                colorAnimator.addUpdateListener { animation ->
                    val color = animation.animatedValue as Int
                    oldView.setBackgroundColor(color)
                }
                colorAnimator.doOnEnd {
                    oldView.background = newDrawable
                }
                val colorFilterMode = PorterDuff.Mode.SRC_ATOP
                val fadeOutDrawableColorAnimator: Animator = ValueAnimator.ofFloat(1f, 0f).apply {
                    addUpdateListener { animation ->
                        val alpha = animation.animatedValue as Float
                        val colorFilter = adjustAlpha(finalNewBackgroundColor!!, alpha)
                        oldView.background.setColorFilter(colorFilter, colorFilterMode)
                    }
                }
                return AnimatorSet().apply {
                    playSequentially(colorAnimator, fadeOutDrawableColorAnimator)
                }
            }

            if (oldDrawable !is ColorDrawable &&
                newDrawable is ColorDrawable
            ) {
                Log.d(TAG, "Transitioning based on non color to color")
                //apply color filter to new background color
                val colorFilterMode = PorterDuff.Mode.SRC_ATOP
                val fadeInDrawableColorAnimator: Animator = ValueAnimator.ofFloat(0f, 1f).apply {
                    addUpdateListener { animation ->
                        val alpha = animation.animatedValue as Float
                        val colorFilter = adjustAlpha(finalNewBackgroundColor!!, alpha)
                        oldView.background.setColorFilter(colorFilter, colorFilterMode)
                    }
                }
                return fadeInDrawableColorAnimator
            }

            if (oldDrawable !is ColorDrawable &&
                newDrawable !is ColorDrawable
            ) {
                Log.d(TAG, "Transitioning based on non color to non color")
                //change to new background color then fade the new drawable in
                //save as the before previous one
//                val colorAnimator: ValueAnimator = ValueAnimator.ofObject(
//                    ArgbEvaluator(),
//                    finalOldBackgroundColor,
//                    finalNewBackgroundColor
//                )
//                colorAnimator.addUpdateListener { animation ->
//                    val color = animation.animatedValue as Int
//                    oldView.setBackgroundColor(color)
//                }
//                colorAnimator.doOnEnd {
//                    oldView.background = newDrawable
//                }

                val colorFilterMode = PorterDuff.Mode.SRC_ATOP

                val fadeInDrawableColorAnimator: Animator = ValueAnimator.ofFloat(0f, 1f).apply {
                    addUpdateListener { animation ->
                        val alpha = animation.animatedValue as Float
                        val colorFilter = adjustAlpha(finalNewBackgroundColor!!, alpha)
                        oldView.background.setColorFilter(colorFilter, colorFilterMode)
                    }
                }
                fadeInDrawableColorAnimator.doOnEnd {
                    oldView.background = newDrawable
                }

                val fadeOutDrawableColorAnimator: Animator = ValueAnimator.ofFloat(1f, 0f).apply {
                    addUpdateListener { animation ->
                        val alpha = animation.animatedValue as Float
                        val colorFilter = adjustAlpha(finalNewBackgroundColor!!, alpha)
                        oldView.background.setColorFilter(colorFilter, colorFilterMode)
                    }
                }
                return AnimatorSet().apply {
                    playSequentially(fadeInDrawableColorAnimator, fadeOutDrawableColorAnimator)
                }
            }
        }

        //NO PREVIOUS BACKGROUND NO ANIMATION TO PLAY JUST SHOW THE NEW BACKGROUND
        return null
    }


    private fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

}