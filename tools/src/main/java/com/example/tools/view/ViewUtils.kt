package com.example.tools.view

import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.tools.R

/**
 * 显示工具
 * @author weitianliang
 */

/**
 * 底部按钮配置
 */
fun addBottomBar(view: BottomNavigationBar) {
    /**
     * 底部 bottombar 配置
     *
     * setMode
     * [MODE_DEFAULT
     * MODE_FIXED
     * MODE_SHIFTING
     * MODE_FIXED_NO_TITLE
     * MODE_SHIFTING_NO_TITLE]
     *
     * setBackgroundStyle
     * [BACKGROUND_STYLE_DEFAULT
     * BACKGROUND_STYLE_STATIC
     * BACKGROUND_STYLE_RIPPLE]
     */
    view.setMode(BottomNavigationBar.MODE_FIXED)
        .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
        .setBarBackgroundColor("#2FA8E1")  // 背景色
        .setInActiveColor("#929292")  // 未选中状态颜色
        .setActiveColor("#ffffff")  // 选中颜色
        .addItem(BottomNavigationItem(R.drawable.hose, "首页"))
        .addItem(BottomNavigationItem(R.drawable.help, "陪护"))
        .addItem(BottomNavigationItem(R.drawable.mine, "我的"))
        .setFirstSelectedPosition(0) // 默认选中位置
        .initialise()
}