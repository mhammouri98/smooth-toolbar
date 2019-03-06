/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/
/*
* Created by Mohammed Alhammouri on 07,February,2019
* myhammouri98@gmail.com
*/
package io.getsmooth.kt.android.toolbar.controller

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapper

interface ToolbarController {

    var parentView: ViewGroup?

    fun init(
        rootView: ViewGroup,
        parentActivity: AppCompatActivity,
        layoutInflater: LayoutInflater,
        canChangeMenuItems: CanChangeMenuItems,
        menuInflater: MenuInflater
    )

    fun setToolbar(toolbarWrapper: ToolbarWrapper?, applyAnimations: Boolean,
                   autoDetection: Boolean)

}