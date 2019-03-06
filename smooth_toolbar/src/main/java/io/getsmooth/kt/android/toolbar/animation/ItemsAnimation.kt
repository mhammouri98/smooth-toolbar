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
import android.view.View


interface ItemsAnimation {

    fun getItemsOutAnimation(
        views: List<View>?
    ): Animator?

    fun getItemsInAnimation(
        views: List<View>?
    ): Animator?

}