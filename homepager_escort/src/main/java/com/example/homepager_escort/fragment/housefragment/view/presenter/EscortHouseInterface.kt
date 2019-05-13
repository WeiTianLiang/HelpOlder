package com.example.homepager_escort.fragment.housefragment.view.presenter

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.example.tools.view.BarChartView
import com.example.tools.view.BaseMapView

/**
 *
 * @author WeiTianLiang
 */
interface EscortHouseInterface {

    // 设置柱状图
    fun setBarChart(barChartView: BarChartView)

    // 设置地图
    fun setMapView(mapView: BaseMapView, activity: Activity, savedInstanceState: Bundle?, locationText: TextView)

    // 设置步数
    fun setStepCount(steCount: TextView)

    // 改变人物
    fun changeOlder(mapView: BaseMapView, activity: Activity, savedInstanceState: Bundle?, locationText: TextView)

    fun setHealthy(textView: TextView)

}