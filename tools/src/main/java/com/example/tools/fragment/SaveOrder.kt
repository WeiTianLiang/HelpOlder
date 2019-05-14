package com.example.tools.fragment

import android.content.Context

object SaveOrder {

    /**
     * 写入
     */
    fun writeFile(cookie: String, context: Context) {
        val editor = context.getSharedPreferences("orderNo", Context.MODE_PRIVATE).edit()
        editor.putString("orderNo", cookie)
        editor.apply()
    }

    /**
     * 读取
     */
    fun readFile(context: Context): String? {
        val preferences = context.getSharedPreferences("orderNo", Context.MODE_PRIVATE)
        return preferences.getString("orderNo", "")
    }

}