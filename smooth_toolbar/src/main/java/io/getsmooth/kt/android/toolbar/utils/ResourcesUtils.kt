/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 06,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.core.content.ContextCompat

object ResourcesUtils {

    fun getLayoutFromId(parentView: ViewGroup?, @LayoutRes viewLayoutId: Int?): View? {
        if (parentView == null || viewLayoutId == null) return null
        return LayoutInflater.from(parentView.context)
            .inflate(viewLayoutId, parentView, false)
    }

    fun getStringFromId(context: Context?, @StringRes stringResId: Int?): String? {
        if (context == null || stringResId == null) return null
        return context.resources.getString(stringResId)
    }

    fun getColorFromId(context: Context?, @ColorRes colorResId: Int?): Int? {
        if (context == null || colorResId == null) return null
        return ContextCompat.getColor(context, colorResId)
    }

    fun getDrawableFromId(context: Context?, @DrawableRes drawableResId: Int?): Drawable? {
        if (context == null || drawableResId == null) return null
        return ContextCompat.getDrawable(context, drawableResId)
    }

    fun getDimenFromId(context: Context?, @DimenRes dimenResId: Int?): Float? {
        if (context == null || dimenResId == null) return null
        return context.resources.getDimension(dimenResId)
    }
}