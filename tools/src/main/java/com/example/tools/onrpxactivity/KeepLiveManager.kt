package com.example.tools.onrpxactivity

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

/**
 * 进程保活策略
 * @author WeiTianLiang
 */
class KeepLiveManager(val activity: Activity) {

    private var sScreenStateReceiver: ScreenStateReceiver? = null

    fun registerBroadCast(context: Context) {
        sScreenStateReceiver = ScreenStateReceiver(activity)
        val intentFilter = IntentFilter()
        // 亮屏时触发
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        // 锁屏时触发
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        // 解锁成功后触发
        intentFilter.addAction(Intent.ACTION_USER_PRESENT)
        context.registerReceiver(sScreenStateReceiver, intentFilter)
    }

    fun unRegisterBroadCast(context: Context) {
        if (sScreenStateReceiver != null) {
            context.unregisterReceiver(sScreenStateReceiver)
        }
    }

    class ScreenStateReceiver(val activity: Activity) : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.e("KeepLiveManager", intent.action)
            if (Intent.ACTION_SCREEN_ON == intent.action || Intent.ACTION_USER_PRESENT == intent.action) { // 亮屏或者解锁时对一像素界面进行销毁
                KeepLiveActivity(activity).destroyOnePixelActivity()
            } else if (Intent.ACTION_SCREEN_OFF == intent.action) { // 锁屏时启动一像素界面，此时进程的oom_adj=0，为android进程中最高优先级，
                // 对应前台进程，通常不会被杀
                KeepLiveActivity(activity).showOnePixelActivity()
            }
        }
    }
}