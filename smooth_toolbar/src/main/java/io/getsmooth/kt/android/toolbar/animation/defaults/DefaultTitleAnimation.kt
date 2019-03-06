/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 02,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.animation.defaults

import android.animation.Animator
import android.widget.TextView
import io.getsmooth.kt.android.toolbar.animation.TitleAnimation

class DefaultTitleAnimation : TitleAnimation {

    override fun getTitleAnimation(
        oldTitleTv: TextView?,
        newTitleTv: TextView?,
        oldTitle: String?,
        newTitle: String?
    ): Animator? = null

}