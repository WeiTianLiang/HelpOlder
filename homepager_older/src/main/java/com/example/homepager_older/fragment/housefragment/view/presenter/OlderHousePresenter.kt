package com.example.homepager_older.fragment.housefragment.view.presenter

import android.graphics.Color
import com.example.tools.view.initBarChartView
import com.example.tools.view.showBarChart
import com.github.mikephil.charting.charts.BarChart

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

    override fun barChart(barChart: BarChart) {
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
        initBarChartView(barChart, xList)
        showBarChart(barChart, listData, "过去五天步数", Color.BLUE)
    }
}
