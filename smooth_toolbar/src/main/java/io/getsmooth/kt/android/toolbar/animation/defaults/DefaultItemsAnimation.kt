/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/
/*
* Created by Mohammed Alhammouri on 09,February,2019
* myhammouri98@gmail.com
*/package io.getsmooth.kt.android.toolbar.animation.defaults

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import io.getsmooth.kt.android.toolbar.animation.ItemsAnimation


class DefaultItemsAnimation(
    context: Context,
    offsetVal: Float = 5f
) : ItemsAnimation {

    val TAG = "ItemsAnimation"

    private val offsetValInPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        offsetVal,
        context.resources.displayMetrics
    )

    override fun getItemsOutAnimation(views: List<View>?): Animator? {
        if (views.isNullOrEmpty()) return null

        val fadeOut = ValueAnimator.ofFloat(1f, 0f)
        fadeOut.addUpdateListener {
            for (view in views) {
                view.alpha = it.animatedValue as Float
            }
        }

        val moveUp = ValueAnimator.ofFloat(0f, offsetValInPx)
        moveUp.addUpdateListener {
            for (view in views) {
                view.translationY = it.animatedValue as Float
            }
        }

        return AnimatorSet().apply {
            playTogether(fadeOut, moveUp)
        }
    }

    override fun getItemsInAnimation(views: List<View>?): Animator? {
        if (views.isNullOrEmpty()) return null

        val fadeIn = ValueAnimator.ofFloat(0f, 1f)
        fadeIn.addUpdateListener {
            for (view in views) {
                view.alpha = it.animatedValue as Float
            }
        }

        val moveIn = ValueAnimator.ofFloat(offsetValInPx, 0f)
        moveIn.addUpdateListener {
            for (view in views) {
                view.translationY = it.animatedValue as Float
            }
        }

        return AnimatorSet().apply {
            playTogether(fadeIn, moveIn)
        }
    }

}