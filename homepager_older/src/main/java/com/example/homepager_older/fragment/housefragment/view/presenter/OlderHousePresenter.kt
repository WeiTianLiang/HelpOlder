package com.example.homepager_older.fragment.housefragment.view.presenter

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import com.example.tools.view.BarChartView
import com.example.tools.view.BaseMapView

/**
 *
 * @author WeiTianLiang
 */
class OlderHousePresenter : OlderHouseInterface {

    /**
     * 柱状图数据
     */
    private val listData = ArrayList<Int>()
    private val xList = ArrayList<String>()

    /**
     * 地图
     */
    override fun setMapView(
        mapView: BaseMapView,
        activity: Activity,
        savedInstanceState: Bundle?,
        locationText: TextView
    ) {
        mapView.onCreate(savedInstanceState, locationText)
        activity.let { mapView.showNowLocation(it) }
    }

    override fun setBarChart(barChartView: BarChartView) {
        listData.add(530)
        listData.add(2230)
        listData.add(630)
        listData.add(740)
        listData.add(960)
        xList.add("5月1")
        xList.add("5月2")
        xList.add("5月3")
        xList.add("5月4")
        xList.add("5月5")
        barChartView.initBarChartView(xList)
        barChartView.showBarChart(listData, "过去五天步数", Color.BLUE)
    }
}