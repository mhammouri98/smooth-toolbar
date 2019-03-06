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
import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import io.getsmooth.kt.android.toolbar.controller.CanChangeMenuItems
import io.getsmooth.kt.android.toolbar.controller.CanChangeTitle
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapper


interface ToolbarAnimationsProvider {

    fun getHideToolbarAnimation(
        oldToolbarWrapper: ToolbarWrapper?
    ): Animator?


    fun getBackgroundAnimation(
        oldView: View?,
        newView: View?,
        oldBackgroundColor: Int?,
        newBackgroundColor: Int?,
        oldDrawable: Drawable?,
        newDrawable: Drawable?
    ): Animator?

    fun getItemsOutAnimation(
        oldViews: List<View>?
    ): Animator?


    fun getItemsInAnimation(
        newViews: List<View>?
    ): Animator?

    fun getItemsAnimation(
        oldViews: List<View>?,
        newViews: List<View>?,
        oldMenuId: Int?,
        newMenuId: Int?,
        canChangeMenuItems: CanChangeMenuItems,
        canChangeTitle: CanChangeTitle,
        oldToolbar: Toolbar?,
        newToolbar: Toolbar?,
        oldTitle: String?,
        newTitle: String?
    ): Animator?

    fun getSizeAnimation(
        oldView: View?,
        newView: View?,
        oldWidth: Float?,
        newWidth: Float?,
        oldHeight: Float?,
        newHeight: Float?
    ): Animator?

    fun getStatusBarAnimation(
        window: Window,
        oldColor: Int?,
        newColor: Int?
    ): Animator?

    fun getForegroundAnimation(
        activity: Activity?,
        oldToolbar: Toolbar?,
        newToolbar: Toolbar?,
        oldViews: List<View>?,
        newViews: List<View>?,
        oldColor: Int?,
        newColor: Int?
    ): Animator?

    fun getTitleAnimation(
        oldTitleTv: TextView?,
        newTitleTv: TextView?,
        oldTitle: String?,
        newTitle: String?
    ): Animator?

    fun getViewsAnimation(
        oldViews: List<View>?,
        newViews: List<View>
    ): Animator?

}