/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 02,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.animation

import android.animation.Animator
import android.os.Build
import android.view.Window
import androidx.annotation.RequiresApi

interface StatusBarAnimation {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getStatusBarAnimation(
        window: Window,
        oldColor: Int?,
        newColor: Int?
    ): Animator?

}