package com.example.homepager_children.fragment.hosuefragment.view

import android.graphics.Color
import android.os.Bundle
import com.example.homepager_children.R
import com.example.tools.fragment.BaseFragment
import kotlinx.android.synthetic.main.children_house_fragment.*

/**
 * 子女 -首页 fragment
 * @author weitianliang
 */
class ChildrenHouseFragment : BaseFragment() {

    private val listData = ArrayList<Int>()
    private val xList = ArrayList<String>()

    override fun onViewCreate(savedInstanceState: Bundle?) {

    }

    override fun onInflated(savedInstanceState: Bundle?) {
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
        barchart.initBarChartView(xList)
        barchart.showBarChart(listData, "过去五天步数", Color.BLUE)
    }

    override fun getLayoutResId(): Int {
        return R.layout.children_house_fragment
    }

}