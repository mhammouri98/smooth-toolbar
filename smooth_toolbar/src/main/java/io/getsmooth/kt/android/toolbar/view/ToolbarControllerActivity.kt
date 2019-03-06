package io.getsmooth.kt.android.toolbar.view


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.getsmooth.kt.android.toolbar.controller.CanChangeMenuItems
import io.getsmooth.kt.android.toolbar.controller.ToolbarController
import io.getsmooth.kt.android.toolbar.controller.ToolbarControllerDelegate
import io.getsmooth.kt.android.toolbar.detector.AutoDetector
import io.getsmooth.kt.android.toolbar.detector.DefaultAutoDetector
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapper


/**
 * A simple [Fragment] subclass.
 *
 */
abstract class ToolbarControllerActivity : AppCompatActivity(), ToolbarController,
    CanChangeMenuItems {

    protected abstract val layoutId: Int
    override var parentView: ViewGroup? = null

    protected open val autoDetector: AutoDetector = DefaultAutoDetector()

    private val toolbarControllerDelegate: ToolbarControllerDelegate = ToolbarControllerDelegate(autoDetector)

    override fun init(
        rootView: ViewGroup,
        parentActivity: AppCompatActivity,
        layoutInflater: LayoutInflater,
        canChangeMenuItems: CanChangeMenuItems,
        menuInflater: MenuInflater
    ) {
        toolbarControllerDelegate.init(
            rootView, parentActivity,
            layoutInflater, canChangeMenuItems,
            menuInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        parentView = (this
            .findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

        init(parentView as ViewGroup, this, LayoutInflater.from(this), this, menuInflater)
    }

    override fun setToolbar(
        toolbarWrapper: ToolbarWrapper?, applyAnimations: Boolean,
        autoDetection: Boolean
    ) {
        toolbarControllerDelegate.setToolbar(toolbarWrapper, applyAnimations, autoDetection)
    }

    override fun changeMenuItems(menuId: Int) {
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean = toolbarControllerDelegate.actualChangeMenuItems(menu)

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = if (item != null
        && toolbarControllerDelegate.onItemSelected(item)
    ) true
    else super.onOptionsItemSelected(item)

}
