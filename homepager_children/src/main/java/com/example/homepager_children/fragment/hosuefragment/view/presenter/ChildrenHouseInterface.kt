package com.example.homepager_children.fragment.hosuefragment.view.presenter

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.example.tools.view.BarChartView
import com.example.tools.view.BaseMapView

/**
 *
 * @author WeiTianLiang
 */
interface ChildrenHouseInterface {

    // 设置柱状图
    fun setBarChart(barChartView: BarChartView)

    // 设置地图
    fun setMapView(mapView: BaseMapView, activity: Activity, savedInstanceState: Bundle?)

    // 设置步数
    fun setStepCount(steCount: TextView)

}