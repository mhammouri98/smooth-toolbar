/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/
/*
* Created by Mohammed Alhammouri on 13,February,2019
* myhammouri98@gmail.com
*/
package io.getsmooth.kt.android.toolbar.animation.defaults

import android.animation.Animator
import android.animation.AnimatorSet
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.doOnEnd
import io.getsmooth.kt.android.toolbar.animation.*
import io.getsmooth.kt.android.toolbar.controller.CanChangeMenuItems
import io.getsmooth.kt.android.toolbar.controller.CanChangeTitle
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapper


class DefaultToolbarAnimationsProvider(
    var context: Context,
    private val backgroundAnimation: BackgroundAnimation = DefaultBackgroundAnimation(),
    private val foregroundAnimation: ForegroundAnimation = DefaultForegroundAnimation(),
    private val itemsAnimation: ItemsAnimation = DefaultItemsAnimation(context),
    private val sizeAnimation: SizeAnimation = DefaultSizeAnimation(),
    private val statusBarAnimation: StatusBarAnimation = DefaultStatusBarAnimation(),
    private val titleAnimation: TitleAnimation = DefaultTitleAnimation(),
    private val viewsAnimation: ViewsAnimation = DefaultViewsAnimation()
) : ToolbarAnimationsProvider {

    val TAG = "toolbarAnimations"

    override fun getHideToolbarAnimation(
        oldToolbarWrapper: ToolbarWrapper?
    ): Animator? = null

    override fun getBackgroundAnimation(
        oldView: View?,
        newView: View?,
        oldBackgroundColor: Int?,
        newBackgroundColor: Int?,
        oldDrawable: Drawable?,
        newDrawable: Drawable?
    ): Animator? =
        backgroundAnimation.getBackgroundAnimation(
            oldView,
            newView,
            oldBackgroundColor,
            newBackgroundColor,
            oldDrawable,
            newDrawable
        )

    override fun getItemsOutAnimation(oldViews: List<View>?): Animator? =
        itemsAnimation.getItemsOutAnimation(oldViews)

    override fun getItemsInAnimation(newViews: List<View>?): Animator? =
        itemsAnimation.getItemsInAnimation(newViews)

    override fun getItemsAnimation(
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
    ): Animator? {
        val itemsOutAnimation = getItemsOutAnimation(oldViews)
        val itemsInAnimation = getItemsInAnimation(oldViews)

        itemsOutAnimation?.doOnEnd {
            if (newMenuId != null) {
                canChangeMenuItems.changeMenuItems(newMenuId)
            }
            canChangeTitle.changeTitle(oldToolbar, newTitle)
        }

        return AnimatorSet().apply {
            if (itemsInAnimation != null &&
                    itemsOutAnimation != null) {
                playSequentially(itemsOutAnimation,itemsInAnimation)
            }
        }
    }

    override fun getSizeAnimation(
        oldView: View?,
        newView: View?,
        oldWidth: Float?,
        newWidth: Float?,
        oldHeight: Float?,
        newHeight: Float?
    ): Animator? =
        sizeAnimation.getSizeAnimation(oldView, newView, oldWidth, newWidth, oldHeight, newHeight)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getStatusBarAnimation(window: Window, oldColor: Int?, newColor: Int?): Animator? =
        statusBarAnimation.getStatusBarAnimation(window, oldColor, newColor)

    override fun getForegroundAnimation(
        activity: Activity?,
        oldToolbar: Toolbar?,
        newToolbar: Toolbar?,
        oldViews: List<View>?,
        newViews: List<View>?,
        oldColor: Int?,
        newColor: Int?
    ): Animator? =
        foregroundAnimation.getForegroundAnimation(
            activity,
            oldToolbar,
            newToolbar,
            oldViews,
            newViews,
            oldColor,
            newColor
        )

    override fun getTitleAnimation(
        oldTitleTv: TextView?,
        newTitleTv: TextView?,
        oldTitle: String?,
        newTitle: String?
    ): Animator? =
        titleAnimation.getTitleAnimation(oldTitleTv, newTitleTv, oldTitle, newTitle)

    override fun getViewsAnimation(oldViews: List<View>?, newViews: List<View>): Animator? =
        viewsAnimation.getViewsAnimation(oldViews, newViews)
}