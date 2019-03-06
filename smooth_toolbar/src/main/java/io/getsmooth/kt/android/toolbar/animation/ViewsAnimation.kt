/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 06,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.animation

import android.animation.Animator
import android.view.View

interface ViewsAnimation {

    fun getViewsAnimation(
        oldViews: List<View>?,
        newViews: List<View>
    ): Animator?

}