package com.example.tools.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.tools.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class BarChartView(
    context: Context,
    attrs: AttributeSet?
) : LinearLayout(context, attrs) {

    private val mBarChart by lazy { findViewById<BarChart>(R.id.barChart) }

    fun initView() {
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
        xAxis.setDrawAxisLine(false)
        leftAxis.setDrawAxisLine(false)
        rightAxis.setDrawAxisLine(false)
        rightAxis.enableGridDashedLine(10f, 10f, 0f)
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

    fun showBarChart(dateValueList: List<Int>, name: String, color: Int) {
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

    private fun initBarDataSet(barDataSet: BarDataSet, color: Int) {
        barDataSet.color = color
        barDataSet.formLineWidth = 1f
        barDataSet.formSize = 15f
        // 不显示柱状图顶部值
        barDataSet.setDrawValues(false)
    }

}