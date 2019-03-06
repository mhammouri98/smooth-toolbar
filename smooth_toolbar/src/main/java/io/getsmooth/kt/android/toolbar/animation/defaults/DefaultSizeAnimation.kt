/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/
/*
* Created by Mohammed Alhammouri on 09,February,2019
* myhammouri98@gmail.com
*/
package io.getsmooth.kt.android.toolbar.animation.defaults

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import io.getsmooth.kt.android.toolbar.animation.SizeAnimation


class DefaultSizeAnimation : SizeAnimation {

    override fun getSizeAnimation(
        oldView: View?,
        newView: View?,
        oldWidth: Float?,
        newWidth: Float?,
        oldHeight: Float?,
        newHeight: Float?
    ): Animator? {
        val transitionView: View? = oldView ?: newView

        val heightAnimation = ValueAnimator.ofFloat(oldHeight ?: 0f, newHeight ?: 0f)

        heightAnimation.addUpdateListener { animation ->
            transitionView?.layoutParams?.also {
                it.height = (animation.animatedValue as Float).toInt()
                transitionView.layoutParams = it
            }
        }

        val widthAnimation = ValueAnimator.ofFloat(oldWidth ?: 0f, newWidth ?: 0f)

        widthAnimation.addUpdateListener { animation ->
            transitionView?.layoutParams?.also {
                it.width = (animation.animatedValue as Float).toInt()
                transitionView.layoutParams = it
            }
        }

        return AnimatorSet().apply {
            playTogether(heightAnimation, widthAnimation)
        }
    }

}