/*
* This project is licensed under Apache License 2.0
* You can find the full license document at the project level license.txt file 
*/


/*
* Created by Mohammed Alhammouri on 06,March,2019
* myhammouri98@gmail.com
*/


package io.getsmooth.kt.android.toolbar.wrapper.auto_detection

data class AutoDetectionConfiguration(
    val detectBackground: Boolean = true,
    val detectStatusBarColor: Boolean = true,
    val detectForeground: Boolean = true,
    val detectTitle: Boolean = true,
    val detectMeasurements: Boolean = true
)