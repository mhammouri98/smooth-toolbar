/*
Copyright 2015 Michal Pawlowski

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package io.getsmooth.kt.android.toolbar.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageButton
import androidx.annotation.ColorInt
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat


/**
 * ToolbarUtils class that iterates through Toolbar views, and sets dynamically icons and texts color
 * Created by chomi3 on 2015-01-19.
 */
object ToolbarUtils {

    /**
     * Use this method to colorizeToolbar toolbar icons to the desired target color
     *
     * @param toolbarView       toolbar view being colored
     * @param toolbarIconsColor the target color of toolbar icons
     * @param activity          reference to activity needed to register observers
     */
    fun colorizeToolbar(
        activity: Activity, toolbarView: Toolbar,
        itemViews: List<View>?, toolbarIconsColor: Int
    ) {
        val colorFilter = PorterDuffColorFilter(toolbarIconsColor, PorterDuff.Mode.SRC_ATOP)

        if (!itemViews.isNullOrEmpty()) {
            for (view in itemViews) {
                //Step 1 : Changing the color of back button (or open drawer button).
                if (view is ImageButton) {
                    //Action Bar back button
                    view.drawable.colorFilter = colorFilter
                }

                if (view is ActionMenuView) {

                    for (j in 0 until view.childCount) {
                        val innerView = view.getChildAt(j)

                        if (innerView is ActionMenuItemView) {

                            for (k in 0 until innerView.compoundDrawables.size) {

                                if (innerView.compoundDrawables[k] != null) {
                                    innerView.post { innerView.compoundDrawables[k].colorFilter = colorFilter }
                                }
                            }
                        }
                    }
                }
            }
        }


        //Step 3: Changing the color of title and subtitle.
        toolbarView.setTitleTextColor(toolbarIconsColor)
        toolbarView.setSubtitleTextColor(toolbarIconsColor)

        //Step 4: Changing the color of the Overflow Menu icon.
        setOverflowButtonColor(
            activity,
            toolbarView,
            toolbarIconsColor
        )
    }


    private fun setOverflowButtonColor(activity: Activity, toolbar: Toolbar, toolbarIconsColor: Int) {
        val decorView = activity.window.decorView as ViewGroup
        val viewTreeObserver = decorView.viewTreeObserver
        colorOverflow(toolbar, toolbarIconsColor)
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                colorOverflow(toolbar, toolbarIconsColor)
                removeOnGlobalLayoutListener(decorView, this)
            }
        })
    }

    private fun colorOverflow(toolbar: Toolbar?, toolbarIconsColor: Int) {

        if (toolbar != null && toolbar.overflowIcon != null) {
            val bg = DrawableCompat.wrap(toolbar.overflowIcon!!)
            DrawableCompat.setTint(bg, toolbarIconsColor)
        }
    }

    internal fun removeOnGlobalLayoutListener(v: View, listener: ViewTreeObserver.OnGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.viewTreeObserver.removeGlobalOnLayoutListener(listener)
        } else {
            v.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }


    // The public static function which can be called from other classes
    fun setStatusBarColor(activity: Activity, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = color
        }
    }

    /**
     * Code to darken the color supplied (mostly color of toolbar)
     */
    fun getDarkenColor(@ColorInt color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= 0.8f
        return Color.HSVToColor(hsv)
    }

}