/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/
/*
* Created by Mohammed Alhammouri on 08,February,2019
* myhammouri98@gmail.com
*/
package io.getsmooth.kt.android.toolbar.animation

import android.animation.Animator
import android.graphics.drawable.Drawable
import android.view.View


interface BackgroundAnimation {

    fun getBackgroundAnimation(
        oldView: View?,
        newView: View?,
        oldBackgroundColor: Int?,
        newBackgroundColor: Int?,
        oldDrawable: Drawable?,
        newDrawable: Drawable?
    ): Animator?

}