package io.getsmooth.kt.android.toolbar.sample


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.getsmooth.kt.android.toolbar.view.ToolbarControllerActivity
import io.getsmooth.kt.android.toolbar.view.ToolbarWrapperFragment
import io.getsmooth.kt.android.toolbar.wrapper.ToolbarWrapper


/**
 * A simple [Fragment] subclass.
 *
 */
class SecondFragment : ToolbarWrapperFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_second, container, false)

    override fun getToolbarWrapper(rootView: ViewGroup?): ToolbarWrapper? =
        ToolbarWrapper(
            layout = LayoutInflater.from(context).inflate(
                R.layout.toolbar_layout2,
                rootView
                , false
            ) as ViewGroup
        )

}

