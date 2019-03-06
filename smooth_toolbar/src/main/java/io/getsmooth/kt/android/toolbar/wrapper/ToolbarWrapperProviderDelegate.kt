/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 01,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.wrapper

import io.getsmooth.kt.android.toolbar.controller.ToolbarController

class ToolbarWrapperProviderDelegate(val toolbarController: ToolbarController) {


    fun invalidateToolbar(
        toolbarWrapper: ToolbarWrapper?, applyAnimations: Boolean,
        autoDetection: Boolean
    ) {
        toolbarController.setToolbar(toolbarWrapper, applyAnimations, autoDetection)
    }

}