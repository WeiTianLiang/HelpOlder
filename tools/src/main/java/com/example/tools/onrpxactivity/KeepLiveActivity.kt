package com.example.tools.onrpxactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.Gravity


/**
 * 保活 activity
 * @author WeiTianLiang
 */
class KeepLiveActivity(
    val activity: Activity
) : Activity() {

    private var keepLiveActivity: KeepLiveActivity? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        keepLiveActivity = this
        initWindow()
    }

    private fun initWindow() {
        val window = window
        window.setGravity(Gravity.LEFT or Gravity.TOP)
        val layoutParams = window.attributes
        layoutParams.x = 0
        layoutParams.y = 0
        layoutParams.height = 1
        layoutParams.width = 1
        window.attributes = layoutParams
    }

    override fun onDestroy() {
        super.onDestroy()
        keepLiveActivity = null
    }

    fun destroyOnePixelActivity() {
        keepLiveActivity?.finish()
    }

    fun showOnePixelActivity() {
        val intent = Intent(activity.applicationContext, this::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.applicationContext.startActivity(intent)
    }

}