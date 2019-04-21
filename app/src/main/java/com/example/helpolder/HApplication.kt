package com.example.helpolder

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 *
 *
 * @author weitianliang
 */

class HApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

}