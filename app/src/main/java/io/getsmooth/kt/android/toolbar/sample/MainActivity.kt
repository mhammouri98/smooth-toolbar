package io.getsmooth.kt.android.toolbar.sample

import android.os.Bundle
import android.os.Handler
import io.getsmooth.kt.android.toolbar.view.ToolbarControllerActivity

class MainActivity : ToolbarControllerActivity() {

    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FirstFragment())
            .commit()

        Handler().postDelayed({
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SecondFragment())
                .commit()
        }, 2000)

    }

}
