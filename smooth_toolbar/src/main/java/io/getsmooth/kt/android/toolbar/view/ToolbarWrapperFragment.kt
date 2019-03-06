package io.getsmooth.kt.android.toolbar.view


import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.getsmooth.kt.android.toolbar.controller.ToolbarController
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapper
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapperProvider
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapperProviderDelegate


/**
 * A simple [Fragment] subclass.
 */
open class ToolbarWrapperFragment : Fragment(), ToolbarWrapperProvider {

    override var applyAnimation: Boolean = true
    override var autoDetect: Boolean = true
    override var toolbarWrapper: ToolbarWrapper? = null

    private lateinit var toolbarWrapperProviderDelegate: ToolbarWrapperProviderDelegate

    private var toolbarController: ToolbarController? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context != null && context is ToolbarController) {
            toolbarController = context
        } else if (parentFragment != null && parentFragment is ToolbarController) {
            toolbarController = parentFragment as ToolbarController
        }

        if (toolbarController == null)
            throw IllegalArgumentException("ToolbarWrapperFragment must have a ToolbarController parent fragment or activity")

        toolbarWrapperProviderDelegate = ToolbarWrapperProviderDelegate(toolbarController!!)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarWrapper = getToolbarWrapper(
            toolbarController?.parentView
        )
        invalidateToolbar()
    }


    protected open fun getToolbarWrapper(rootView: ViewGroup?): ToolbarWrapper? = null

    override fun invalidateToolbar(
        toolbarWrapper: ToolbarWrapper?,
        applyAnimations: Boolean,
        autoDetection: Boolean
    ) {
        toolbarWrapperProviderDelegate.invalidateToolbar(toolbarWrapper, applyAnimations, autoDetection)
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbarWrapper = null
    }

}
