/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/
/*
* Created by Mohammed Alhammouri on 07,February,2019
* myhammouri98@gmail.com
*/
package io.getsmooth.kt.android.toolbar.wrapper

interface ToolbarWrapperProvider {

    var applyAnimation: Boolean
    var autoDetect: Boolean
    var toolbarWrapper: ToolbarWrapper?

    fun invalidateToolbar(
        toolbarWrapper: ToolbarWrapper? = this.toolbarWrapper,
        applyAnimations: Boolean = applyAnimation,
        autoDetection: Boolean = autoDetect
    )

}