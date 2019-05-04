package com.example.tools.view

import android.graphics.Color
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.tools.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

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

fun initBarChartView(mBarChart: BarChart, xValueList: List<String>) {
    // 背景颜色
    mBarChart.setBackgroundColor(Color.WHITE)
    // 不显示图表网格
    mBarChart.setDrawGridBackground(false)
    // 背景阴影
    mBarChart.setDrawBarShadow(false)
    mBarChart.isHighlightFullBarEnabled = false
    // 不显示图表边框
    mBarChart.setDrawBorders(false)

    // 不显示右下角描述内容
    val description = Description()
    description.isEnabled = false
    mBarChart.description = description

    /** XY 轴的设置 **/
    //X轴设置显示位置在底部
    val xAxis = mBarChart.xAxis
    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.setDrawGridLines(false)

    val leftAxis = mBarChart.axisLeft
    val rightAxis = mBarChart.axisRight

    // 不显示X轴 Y轴线条
    setXValue(xAxis, xValueList)
    leftAxis.setDrawAxisLine(false)
    rightAxis.setDrawAxisLine(false)
    rightAxis.enableGridDashedLine(10f, 20f, 0f)
    leftAxis.isEnabled = false

    /***折线图例 标签 设置***/
    val legend = mBarChart.legend
    legend.form = Legend.LegendForm.LINE
    legend.textSize = 11f
    // 显示位置
    legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
    legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
    legend.orientation = Legend.LegendOrientation.HORIZONTAL
    // 是否绘制在图表里面
    legend.setDrawInside(false)
}

fun showBarChart(mBarChart: BarChart, dateValueList: List<Int>, name: String, color: Int) {
    val entries = ArrayList<BarEntry>()
    for (i in 0 until dateValueList.size) {
        val barEntry = BarEntry(i.toFloat(), dateValueList[i].toFloat())
        entries.add(barEntry)
    }

    // 每一个 BarDataSet 代表一类柱状图
    val barDataSet = BarDataSet(entries, name)
    initBarDataSet(barDataSet, color)

    val data = BarData(barDataSet)
    mBarChart.data = data
}

/**
 * 设置x轴的值
 */
private fun setXValue(xAxis: XAxis, xValueList: List<String>) {
    xAxis.setDrawAxisLine(false)
    xAxis.labelCount = 5
    xAxis.valueFormatter = IndexAxisValueFormatter(xValueList)
}

private fun initBarDataSet(barDataSet: BarDataSet, color: Int) {
    barDataSet.color = color
    barDataSet.formLineWidth = 1f
    barDataSet.formSize = 15f
    // 不显示柱状图顶部值
    barDataSet.setDrawValues(true)
}
