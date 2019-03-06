/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/
/*
* Created by Mohammed Alhammouri on 07,February,2019
* myhammouri98@gmail.com
*/
package io.getsmooth.kt.android.toolbar.wrapper

import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.*
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.getsmooth.kt.android.toolbar.animation.ToolbarAnimationsProvider
import io.getsmooth.kt.android.toolbar.utils.ResourcesUtils.getColorFromId
import io.getsmooth.kt.android.toolbar.utils.ResourcesUtils.getDimenFromId
import io.getsmooth.kt.android.toolbar.utils.ResourcesUtils.getDrawableFromId
import io.getsmooth.kt.android.toolbar.utils.ResourcesUtils.getLayoutFromId
import io.getsmooth.kt.android.toolbar.utils.ResourcesUtils.getStringFromId
import io.getsmooth.kt.android.toolbar.wrapper.animation.AnimationConfiguration
import io.getsmooth.kt.android.toolbar.wrapper.auto_detection.AutoDetectionConfiguration

interface OnMenuItemInteraction {
    fun onOptionsItemSelected(item: MenuItem): Boolean
}

class ToolbarWrapper(
    var layout: ViewGroup? = null,
    var title: String? = null,
    @MenuRes val menuId: Int? = null,
    var statusBarColor: Int? = null,
    var foregroundColor: Int? = null,
    var width: Float? = null,
    var height: Float? = null,
    var backgroundDrawable: Drawable? = null,
    var backgroundColor: Int? = null,
    var onMenuItemInteraction: OnMenuItemInteraction? = null,
    var animationsProvider: ToolbarAnimationsProvider? = null,
    val autoDetectionConfiguration: AutoDetectionConfiguration = AutoDetectionConfiguration(),
    val animationConfiguration: AnimationConfiguration = AnimationConfiguration()
) {

    constructor(
        rootView: ViewGroup?,
        @LayoutRes layoutId: Int,
        @StringRes titleRes: Int? = null,
        @MenuRes menuId: Int? = null,
        @ColorRes statusBarColorId: Int? = null,
        @ColorRes foregroundColorId: Int? = null,
        @DimenRes widthId: Int? = null,
        @DimenRes heightId: Int? = null,
        @DrawableRes backgroundDrawableId: Int? = null,
        @ColorRes backgroundColorId: Int? = null,
        onMenuItemInteraction: OnMenuItemInteraction? = null,
        animationsProvider: ToolbarAnimationsProvider? = null,
        autoDetectionConfiguration: AutoDetectionConfiguration = AutoDetectionConfiguration(),
        animationConfiguration: AnimationConfiguration = AnimationConfiguration()
    ) : this(
        getLayoutFromId(rootView, layoutId) as ViewGroup,
        getStringFromId(rootView?.context, titleRes),
        menuId,
        getColorFromId(rootView?.context, statusBarColorId),
        getColorFromId(rootView?.context, foregroundColorId),
        getDimenFromId(rootView?.context, widthId),
        getDimenFromId(rootView?.context, heightId),
        getDrawableFromId(rootView?.context, backgroundDrawableId),
        getColorFromId(rootView?.context, backgroundColorId),
        onMenuItemInteraction,
        animationsProvider,
        autoDetectionConfiguration,
        animationConfiguration
    )

    internal var appBarLayout: AppBarLayout? = null
    internal var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    internal var toolbar: Toolbar? = null
    internal var titleTv: TextView? = null
    internal var itemViews: MutableList<View> = arrayListOf()
    internal var otherViews: MutableList<View> = arrayListOf()


    override fun toString(): String {
        return "ToolbarWrapper(layout=$layout, title=$title, menuId=$menuId, statusBarColor=$statusBarColor, foregroundColor=$foregroundColor, width=$width, height=$height, backgroundDrawable=$backgroundDrawable, backgroundColor=$backgroundColor, onMenuItemInteraction=$onMenuItemInteraction, animationsProvider=$animationsProvider, autoDetectionConfiguration=$autoDetectionConfiguration, animationConfiguration=$animationConfiguration, appBarLayout=$appBarLayout, collapsingToolbarLayout=$collapsingToolbarLayout, toolbar=$toolbar, titleTv=$titleTv, itemViews=$itemViews, otherViews=$otherViews)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToolbarWrapper

        if (layout != other.layout) return false
        if (title != other.title) return false
        if (menuId != other.menuId) return false
        if (statusBarColor != other.statusBarColor) return false
        if (foregroundColor != other.foregroundColor) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (backgroundDrawable != other.backgroundDrawable) return false
        if (backgroundColor != other.backgroundColor) return false
        if (onMenuItemInteraction != other.onMenuItemInteraction) return false
        if (animationsProvider != other.animationsProvider) return false
        if (autoDetectionConfiguration != other.autoDetectionConfiguration) return false
        if (animationConfiguration != other.animationConfiguration) return false
        if (appBarLayout != other.appBarLayout) return false
        if (collapsingToolbarLayout != other.collapsingToolbarLayout) return false
        if (toolbar != other.toolbar) return false
        if (titleTv != other.titleTv) return false
        if (itemViews != other.itemViews) return false
        if (otherViews != other.otherViews) return false

        return true
    }

    override fun hashCode(): Int {
        var result = layout?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (menuId ?: 0)
        result = 31 * result + (statusBarColor ?: 0)
        result = 31 * result + (foregroundColor ?: 0)
        result = 31 * result + (width?.hashCode() ?: 0)
        result = 31 * result + (height?.hashCode() ?: 0)
        result = 31 * result + (backgroundDrawable?.hashCode() ?: 0)
        result = 31 * result + (backgroundColor ?: 0)
        result = 31 * result + (onMenuItemInteraction?.hashCode() ?: 0)
        result = 31 * result + (animationsProvider?.hashCode() ?: 0)
        result = 31 * result + autoDetectionConfiguration.hashCode()
        result = 31 * result + animationConfiguration.hashCode()
        result = 31 * result + (appBarLayout?.hashCode() ?: 0)
        result = 31 * result + (collapsingToolbarLayout?.hashCode() ?: 0)
        result = 31 * result + (toolbar?.hashCode() ?: 0)
        result = 31 * result + (titleTv?.hashCode() ?: 0)
        result = 31 * result + itemViews.hashCode()
        result = 31 * result + otherViews.hashCode()
        return result
    }


}