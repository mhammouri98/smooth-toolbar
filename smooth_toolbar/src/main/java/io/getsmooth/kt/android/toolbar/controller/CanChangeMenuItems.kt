/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/
/*
* Created by Mohammed Alhammouri on 10,February,2019
* myhammouri98@gmail.com
*/
package io.getsmooth.kt.android.toolbar.controller

import androidx.annotation.MenuRes

interface CanChangeMenuItems{
    fun changeMenuItems(@MenuRes menuId: Int)
}
