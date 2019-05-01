package com.example.homepager_older.fragment.housefragment.view.view

import android.graphics.Color
import android.os.Bundle
import com.example.homepager_older.R
import com.example.tools.fragment.BaseFragment
import com.example.tools.view.initBarChartView
import com.example.tools.view.showBarChart
import kotlinx.android.synthetic.main.older_house_fragment.*

/**
 * 老人 - 首页 fragment
 * @author weitianliang
 */
class HouseFragment : BaseFragment() {

    private val listData = ArrayList<Int>()

    override fun onViewCreate() {

    }

    override fun onInflated(savedInstanceState: Bundle?) {
        for (i in 0 until 5) {
            listData.add(i + 100)
        }
        initBarChartView(barChart)
        showBarChart(barChart, listData, "步数", Color.BLUE)
    }

    override fun getLayoutResId(): Int {
        return R.layout.older_house_fragment
    }
}
