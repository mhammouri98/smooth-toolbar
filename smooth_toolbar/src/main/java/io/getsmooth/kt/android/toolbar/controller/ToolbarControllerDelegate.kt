/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file
*/


/*
* Created by Mohammed Alhammouri on 01,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.controller

import android.animation.Animator
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.doOnEnd
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.getsmooth.kt.android.toolbar.animation.ToolbarAnimationsProvider
import io.getsmooth.kt.android.toolbar.animation.defaults.DefaultToolbarAnimationsProvider
import io.getsmooth.kt.android.toolbar.detector.AutoDetector
import io.getsmooth.kt.android.toolbar.extras_maybe.SmoothToolbarConfig
import io.getsmooth.kt.android.toolbar.utils.ToolbarUtils
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapper


private const val LAYOUT_CHANGED_KEY = 1
private const val TITLE_CHANGED_KEY = 2
private const val ITEMS_CHANGED_KEY = 3
private const val BACKGROUND_CHANGED_KEY = 4
private const val SIZE_CHANGED_KEY = 5
private const val FOREGROUND_CHANGED_KEY = 6
private const val STATUS_BAR_CHANGED_KEY = 7

class ToolbarControllerDelegate(
    private val autoDetector: AutoDetector
) : ToolbarController, CanChangeMenuItems, CanChangeTitle {

    private var newToolbarWrapper: ToolbarWrapper? = null
    private var oldToolbarWrapper: ToolbarWrapper? = null

    private lateinit var activity: AppCompatActivity
    private lateinit var window: Window
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var canChangeMenuItems: CanChangeMenuItems
    private lateinit var menuInflater: MenuInflater
    override var parentView: ViewGroup? = null

    override fun init(
        rootView: ViewGroup, parentActivity: AppCompatActivity, layoutInflater: LayoutInflater,
        canChangeMenuItems: CanChangeMenuItems,
        menuInflater: MenuInflater
    ) {
        this.parentView = rootView
        this.activity = parentActivity
        this.window = activity.window
        this.layoutInflater = layoutInflater
        this.canChangeMenuItems = canChangeMenuItems
        this.menuInflater = menuInflater

        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y
    }


    private fun extractViews(toolbarWrapper: ToolbarWrapper?, toolbarLayout: ViewGroup?) {
        if (toolbarLayout == null) {
            toolbarWrapper?.appBarLayout = null
            toolbarWrapper?.collapsingToolbarLayout = null
            toolbarWrapper?.toolbar = null
            toolbarWrapper?.titleTv = null
            toolbarWrapper?.itemViews = arrayListOf()
            toolbarWrapper?.otherViews = arrayListOf()
            return
        }

        toolbarWrapper?.appBarLayout = toolbarLayout as? AppBarLayout
            ?: throw IllegalArgumentException("Toolbar layout must always start with AppbarLayout")

        toolbarWrapper?.appBarLayout?.also {
            for (i in 0 until it.childCount) {
                val view = it.getChildAt(i)
                when (view) {
                    is Toolbar -> toolbarWrapper.toolbar = view
                    is CollapsingToolbarLayout -> toolbarWrapper.collapsingToolbarLayout = view
                    else -> toolbarWrapper.otherViews.add(view)
                }
            }
        }

        toolbarWrapper?.toolbar.also {
            if (it == null) {
                throw IllegalArgumentException("No toolbar found")
            }

            for (i in 0..it.childCount) {
                val view = it.getChildAt(i) ?: continue
                if (view is TextView && view.text == it.title) {
                    toolbarWrapper?.titleTv = view
                }
                toolbarWrapper?.itemViews?.add(view)
            }
        }
    }

    override fun setToolbar(
        toolbarWrapper: ToolbarWrapper?, applyAnimations: Boolean,
        autoDetection: Boolean
    ) {
        oldToolbarWrapper = newToolbarWrapper
        newToolbarWrapper = toolbarWrapper
        newToolbarWrapper?.also {
            if (it.animationsProvider == null) {
                it.animationsProvider = DefaultToolbarAnimationsProvider(activity)
            }

            it.layout?.visibility = View.INVISIBLE
            if (it.layout?.parent == null) {
                parentView?.addView(it.layout, 0)
            }
            extractViews(it, it.layout)

            if (!autoDetection) {
                startChange(applyAnimations)
                return
            }

            autoDetector.detectConfiguration(
                it,
                parentView,
                screenWidth, screenHeight,
                object : AutoDetector.DetectionListener {
                    override fun onDetectionFinished(
                        backgroundColor: Int?,
                        backgroundDrawable: Drawable?,
                        foregroundColor: Int?,
                        statusBarColor: Int?,
                        width: Float?,
                        height: Float?,
                        title: String?
                    ) {

                        if (it.backgroundColor == null) it.backgroundColor =
                            backgroundColor ?: SmoothToolbarConfig.DEFAULT_BACKGROUND_COLOR
                        if (it.backgroundDrawable == null) it.backgroundDrawable =
                            backgroundDrawable ?: ColorDrawable(it.backgroundColor!!)
                        if (it.foregroundColor == null) it.foregroundColor =
                            foregroundColor ?: SmoothToolbarConfig.DEFAULT_FOREGROUND_COLOR
                        if (it.statusBarColor == null) it.statusBarColor =
                            statusBarColor ?: SmoothToolbarConfig.DEFAULT_STATUS_COLOR
                        if (it.width == null) it.width = width ?: SmoothToolbarConfig.DEFAULT_WIDTH
                        if (it.height == null) it.height =
                            height ?: SmoothToolbarConfig.DEFAULT_HEIGHT
                        if (it.title == null) it.title = title ?: SmoothToolbarConfig.DEFAULT_TITLE

                        startChange(applyAnimations)
                    }
                })
        }

    }


    private fun startChange(applyAnimations: Boolean) {
        if (applyAnimations && oldToolbarWrapper != null) {
            makeAnimatedChanges()
        } else {
            makeChanges()
        }
    }

    private fun getAnimationByChangeNumber(change: Int, changeIfNull: Boolean = true): Animator? {
        var animation: Animator? = null
        when (change) {
            LAYOUT_CHANGED_KEY -> animation = getLayoutAnimation(
                newToolbarWrapper?.animationsProvider, oldToolbarWrapper, newToolbarWrapper
            )
            TITLE_CHANGED_KEY ->
                if (newToolbarWrapper?.animationConfiguration?.animateTitle != false) {
                    animation = getTitleAnimation(
                        newToolbarWrapper?.animationsProvider,
                        newToolbarWrapper?.toolbar,
                        oldToolbarWrapper?.titleTv,
                        newToolbarWrapper?.titleTv,
                        oldToolbarWrapper?.title,
                        newToolbarWrapper?.title
                    )
                }
            ITEMS_CHANGED_KEY ->
                if (newToolbarWrapper?.animationConfiguration?.animateItems != false) {
                    animation = getItemsAnimation(
                        newToolbarWrapper?.animationsProvider,
                        oldToolbarWrapper?.itemViews,
                        newToolbarWrapper?.itemViews,
                        oldToolbarWrapper?.menuId,
                        newToolbarWrapper?.menuId,
                        oldToolbarWrapper?.toolbar,
                        newToolbarWrapper?.toolbar,
                        oldToolbarWrapper?.title,
                        newToolbarWrapper?.title
                    )
                }
            BACKGROUND_CHANGED_KEY ->
                if (newToolbarWrapper?.animationConfiguration?.animateBackground != false) {
                    animation = getBackgroundAnimation(
                        newToolbarWrapper?.animationsProvider,
                        oldToolbarWrapper?.layout,
                        newToolbarWrapper?.layout,
                        oldToolbarWrapper?.backgroundDrawable,
                        newToolbarWrapper?.backgroundDrawable,
                        oldToolbarWrapper?.backgroundColor,
                        newToolbarWrapper?.backgroundColor
                    )
                }
            SIZE_CHANGED_KEY ->
                if (newToolbarWrapper?.animationConfiguration?.animateSize != false) {
                    animation = getSizeAnimation(
                        newToolbarWrapper?.animationsProvider,
                        oldToolbarWrapper?.layout,
                        newToolbarWrapper?.layout,
                        oldToolbarWrapper?.width,
                        newToolbarWrapper?.width,
                        oldToolbarWrapper?.height,
                        newToolbarWrapper?.height
                    )
                }
            FOREGROUND_CHANGED_KEY ->
                if (newToolbarWrapper?.animationConfiguration?.animateForeground != false) {
                    animation = getForegroundAnimation(
                        newToolbarWrapper?.animationsProvider,
                        oldToolbarWrapper?.toolbar,
                        newToolbarWrapper?.toolbar,
                        oldToolbarWrapper?.itemViews,
                        newToolbarWrapper?.itemViews,
                        oldToolbarWrapper?.foregroundColor,
                        newToolbarWrapper?.foregroundColor
                    )
                }
            STATUS_BAR_CHANGED_KEY ->
                if (newToolbarWrapper?.animationConfiguration?.animateStatusBar != false) {
                    animation = getStatusBarAnimation(
                        newToolbarWrapper?.animationsProvider,
                        oldToolbarWrapper?.statusBarColor,
                        newToolbarWrapper?.statusBarColor
                    )
                }
        }

        if (animation == null && changeIfNull) {
            makeChangeByChangeNumber(change)
        }
        return animation
    }

    private fun makeChangeByChangeNumber(change: Int) {
        when (change) {
            LAYOUT_CHANGED_KEY -> changeLayout()
            TITLE_CHANGED_KEY -> if (newToolbarWrapper != null && newToolbarWrapper!!.toolbar != null)
                changeTitle(
                    newToolbarWrapper!!.toolbar!!,
                    newToolbarWrapper?.title
                )

            ITEMS_CHANGED_KEY -> if (newToolbarWrapper != null &&
                newToolbarWrapper?.menuId != null
            ) changeMenuItems(newToolbarWrapper!!.menuId!!)

            BACKGROUND_CHANGED_KEY ->
                if (newToolbarWrapper != null &&
                    newToolbarWrapper!!.layout != null
                )
                    changeBackground(
                        newToolbarWrapper!!.layout!!, newToolbarWrapper!!.backgroundDrawable,
                        newToolbarWrapper!!.backgroundColor
                    )

            SIZE_CHANGED_KEY ->
                if (newToolbarWrapper != null &&
                    newToolbarWrapper!!.layout != null
                )
                    changeSize(
                        newToolbarWrapper!!.layout!!,
                        newToolbarWrapper?.width,
                        newToolbarWrapper?.height
                    )
            FOREGROUND_CHANGED_KEY -> if (newToolbarWrapper != null &&
                newToolbarWrapper!!.toolbar != null
            )
                changeForeground(
                    newToolbarWrapper!!.toolbar!!,
                    newToolbarWrapper?.itemViews,
                    newToolbarWrapper?.foregroundColor
                )
            STATUS_BAR_CHANGED_KEY -> if (newToolbarWrapper != null) changeStatusBar(
                newToolbarWrapper?.statusBarColor
            )
        }
    }

    private fun makeChanges() {
        for (change in getChanges(oldToolbarWrapper, newToolbarWrapper)) {
            makeChangeByChangeNumber(change)
        }
    }

    val TAG = "toolbarController"

    private fun makeAnimatedChanges() {
        val animationsToApply: MutableList<Animator> = arrayListOf()
        for (change in getChanges(oldToolbarWrapper, newToolbarWrapper)) {
            getAnimationByChangeNumber(change)?.also {
                animationsToApply.add(it)
            }
        }

        AnimatorSet().apply {
            playTogether(animationsToApply)
            duration = newToolbarWrapper?.animationConfiguration?.duration
                ?: SmoothToolbarConfig.DEFAULT_DURATION
            interpolator = newToolbarWrapper?.animationConfiguration?.animationInterpolator
                ?: SmoothToolbarConfig.DEFAULT_ANIMATION_INTERPOLATOR
            doOnEnd {
                changeLayout()
            }
            start()
        }
    }


    private fun getChanges(
        oldToolbar: ToolbarWrapper?,
        newToolbar: ToolbarWrapper?
    ): List<Int> {
        if (oldToolbar == null || newToolbar == null ||
            oldToolbar.layout != newToolbar.layout
        ) return arrayListOf(LAYOUT_CHANGED_KEY)
        val changesList: MutableList<Int> = arrayListOf()

        if (oldToolbar.backgroundDrawable != newToolbar.backgroundDrawable ||
            oldToolbar.backgroundColor != newToolbar.backgroundColor
        ) changesList.add(BACKGROUND_CHANGED_KEY)

        if (oldToolbar.width != newToolbar.width ||
            oldToolbar.height != newToolbar.height
        ) changesList.add(SIZE_CHANGED_KEY)

        if (oldToolbar.menuId != newToolbar.menuId) changesList.add(ITEMS_CHANGED_KEY)

        if (oldToolbar.title != newToolbar.title) changesList.add(TITLE_CHANGED_KEY)

        if (oldToolbar.statusBarColor != newToolbar.statusBarColor) changesList.add(
            STATUS_BAR_CHANGED_KEY
        )

        if (oldToolbar.foregroundColor != newToolbar.foregroundColor) changesList.add(
            FOREGROUND_CHANGED_KEY
        )

        return changesList
    }

    @SuppressLint("NewApi")
    private fun changeStatusBar(statusBarColor: Int?) {
        if (!prepareStatusBarForColorChange()) return
        if (statusBarColor == null) return

        window.statusBarColor = statusBarColor
    }

    private fun changeForeground(toolbar: Toolbar, itemViews: List<View>?, foregroundColor: Int?) {
        ToolbarUtils.colorizeToolbar(
            activity,
            toolbar,
            itemViews,
            foregroundColor ?: SmoothToolbarConfig.DEFAULT_FOREGROUND_COLOR
        )
    }

    private fun changeSize(view: View, width: Float?, height: Float?) {
        view.layoutParams?.also {
            it.width = width?.toInt() ?: 0
            it.height = height?.toInt() ?: 0
            view.layoutParams = it
        }
    }

    private fun changeBackground(
        view: View,
        backgroundDrawable: Drawable?,
        backoffBackgroundColor: Int?
    ) {
        val background =
            backgroundDrawable ?: if (backoffBackgroundColor != null) ColorDrawable(
                backoffBackgroundColor
            )
            else ColorDrawable(SmoothToolbarConfig.DEFAULT_BACKGROUND_COLOR)

        view.background = background
    }

    private
    var menuId: Int? = null

    override fun changeMenuItems(menuId: Int) {
        canChangeMenuItems.changeMenuItems(menuId)
    }

    internal fun actualChangeMenuItems(menu: Menu): Boolean {
        menuId?.also {
            menuInflater.inflate(it, menu)
            return true
        }
        return false
    }

    private fun changeLayout() {
        parentView?.removeView(oldToolbarWrapper?.layout)

        newToolbarWrapper?.also {
            it.layout?.visibility = View.VISIBLE
            activity.setSupportActionBar(it.toolbar)
            applyAllChanges(it)
        }

        if (newToolbarWrapper == null) {
            //TODO : Hide appbar
        }

    }

    private fun applyAllChanges(toolbarWrapper: ToolbarWrapper) {
        if (toolbarWrapper.menuId != null) changeMenuItems(toolbarWrapper.menuId)
        changeStatusBar(toolbarWrapper.statusBarColor)
        if (toolbarWrapper.toolbar != null) {
            changeTitle(toolbarWrapper.toolbar!!, toolbarWrapper.title)
            changeForeground(
                toolbarWrapper.toolbar!!,
                toolbarWrapper.itemViews,
                toolbarWrapper.foregroundColor
            )
        }
        if (toolbarWrapper.layout != null) {
            changeSize(toolbarWrapper.layout!!, toolbarWrapper.width, toolbarWrapper.height)
            changeBackground(
                toolbarWrapper.layout!!,
                toolbarWrapper.backgroundDrawable,
                toolbarWrapper.backgroundColor
            )
        }
    }

    private fun prepareStatusBarForColorChange(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return false
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        return true
    }

    @SuppressLint("NewApi")
    private fun getStatusBarAnimation(
        animationsProvider: ToolbarAnimationsProvider?,
        oldStatusBarColor: Int?,
        newStatusBarColor: Int?
    ): Animator
    ? {
        if (!prepareStatusBarForColorChange()) return null

        return animationsProvider?.getStatusBarAnimation(
            window,
            oldStatusBarColor,
            newStatusBarColor
        ).also {
            if (it == null) {
                changeStatusBar(newStatusBarColor)
                return it
            }
            it.doOnEnd {
                changeStatusBar(newStatusBarColor)
            }
        }
    }

    private fun getForegroundAnimation(
        animationsProvider: ToolbarAnimationsProvider?,
        oldToolbar: Toolbar?,
        newToolbar: Toolbar?,
        oldViews: List<View>?,
        newViews: List<View>?,
        oldForegroundColor: Int?,
        newForegroundColor: Int?
    ): Animator
    ? = animationsProvider?.getForegroundAnimation(
        activity, oldToolbar, newToolbar,
        oldViews, newViews, oldForegroundColor, newForegroundColor
    )?.also {
        it.doOnEnd {
            if (newToolbar != null) {
                changeForeground(newToolbar, newViews, newForegroundColor)
            }
        }
    }

    private fun getSizeAnimation(
        animationsProvider: ToolbarAnimationsProvider?,
        oldView: View?,
        newView: View?,
        oldWidth: Float?,
        newWidth: Float?,
        oldHeight: Float?,
        newHeight: Float?
    ): Animator
    ? = animationsProvider?.getSizeAnimation(
        oldView, newView,
        oldWidth ?: 0f, newWidth ?: 0f,
        oldHeight ?: 0f, newHeight ?: 0f
    )?.also {
        it.doOnEnd {
            if (newView != null) {
                changeSize(newView, newWidth, newHeight)
            }
        }
    }


    private fun getBackgroundAnimation(
        animationsProvider: ToolbarAnimationsProvider?,
        oldView: View?,
        newView: View?,
        oldBackgroundDrawable: Drawable?,
        newBackgroundDrawable: Drawable?,
        oldBackoffBackgroundColor: Int?,
        newBackoffBackgroundColor: Int?
    ): Animator? = animationsProvider?.getBackgroundAnimation(
        oldView, newView, oldBackoffBackgroundColor, newBackoffBackgroundColor
        , oldBackgroundDrawable, newBackgroundDrawable
    )?.also {
        it.doOnEnd {
            if (newView != null) {
                changeBackground(newView, newBackgroundDrawable, newBackoffBackgroundColor)
            }
        }
    }

    private fun getItemsAnimation(
        animationsProvider: ToolbarAnimationsProvider?,
        oldViews: MutableList<View>?,
        newViews: MutableList<View>?,
        @MenuRes oldMenuId: Int?,
        @MenuRes newMenuId: Int?,
        oldToolbar: Toolbar?,
        newToolbar: Toolbar?,
        oldTitle: String?,
        newTitle: String?
    ): Animator
    ? = animationsProvider?.getItemsAnimation(
        oldViews, newViews, oldMenuId, newMenuId, this,
        this,
        oldToolbar, newToolbar,
        oldTitle, newTitle
    )


    private fun getTitleAnimation(
        animationsProvider: ToolbarAnimationsProvider?,
        toolbar: Toolbar?,
        oldTitleTv: TextView?,
        newTitleTv: TextView?,
        oldTitle: String?,
        newTitle: String?
    ): Animator? =
        animationsProvider?.getTitleAnimation(oldTitleTv, newTitleTv, oldTitle, newTitle)
            ?.also {
                it.doOnEnd {
                    if (toolbar != null) {
                        changeTitle(toolbar, newTitle)
                    }
                }
            }

    private fun getLayoutAnimation(
        animationsProvider: ToolbarAnimationsProvider?,
        oldToolbarWrapper: ToolbarWrapper?,
        newToolbarWrapper: ToolbarWrapper?
    ): Animator? =
        if (newToolbarWrapper != null) {

            val animations: MutableList<Animator> = arrayListOf()
            for (i in 2..7) {
                getAnimationByChangeNumber(i, false)?.also {
                    animations.add(it)
                }
            }

            AnimatorSet().apply {
                playTogether(animations)
            }
        } else {
            val animation = animationsProvider?.getHideToolbarAnimation(oldToolbarWrapper)
            if (animation == null) changeLayout()
            animation
        }


    internal fun onItemSelected(item: MenuItem): Boolean =
        newToolbarWrapper?.onMenuItemInteraction?.onOptionsItemSelected(item) ?: false

    override fun changeTitle(
        toolbar: Toolbar?,
        title: String?
    ) {
        Log.d(TAG, "Changing title : " + title)
        if (toolbar == null) return
        toolbar.title = title ?: ""
    }

}