package com.example.homepager_children.fragment.hosuefragment.view.view

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.widget.TextView
import com.example.homepager_children.R
import com.example.homepager_children.fragment.hosuefragment.view.presenter.ChildrenHousePresenter
import com.example.tools.fragment.BaseFragment
import com.example.tools.view.BaseMapView
import kotlinx.android.synthetic.main.children_house_fragment.*

/**
 * 子女 -首页 fragment
 * @author weitianliang
 */
class ChildrenHouseFragment : BaseFragment() {

    private val mapView by lazy { findViewById<BaseMapView>(R.id.mapView) }


    private val presenter by lazy { ChildrenHousePresenter() }

    override fun onViewCreate(savedInstanceState: Bundle?) {

        activity?.let { presenter.setMapView(mapView, it, savedInstanceState) }
    }

    override fun onInflated(savedInstanceState: Bundle?) {
        presenter.setBarChart(barChartView)
        presenter.setStepCount(stepCount)
    }

    override fun getLayoutResId(): Int {
        return R.layout.children_house_fragment
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

}