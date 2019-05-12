package com.example.homepager_older.fragment.housefragment.view.tools

import android.content.Context

/**
 *
 * 本地保存当天的数据
 * @author WeiTianLiang
 */
object SharePreferenceStep {

    /**
     * 写入
     */
    fun writeStep(step: Int, context: Context) {
        val editor = context.getSharedPreferences("step", Context.MODE_PRIVATE).edit()
        editor.putInt("step", step)
        editor.apply()
    }

    /**
     * 读取
     */
    fun readStep(context: Context): Int {
        val preferences = context.getSharedPreferences("step", Context.MODE_PRIVATE)
        return preferences.getInt("step", 0)
    }
}