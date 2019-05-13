package com.example.homepager_escort.fragment.housefragment.view.view

import android.os.Bundle
import android.widget.TextView
import com.example.homepager_escort.R
import com.example.homepager_escort.fragment.housefragment.view.presenter.EscortHousePresenter
import com.example.tools.fragment.BaseFragment
import com.example.tools.view.BaseMapView
import kotlinx.android.synthetic.main.house_fragment.*

/**
 * 老人 -主页面
 * @author weitianliang
 */
class HouseFragment : BaseFragment() {

    private val mapView by lazy { findViewById<BaseMapView>(R.id.mapView) }

    private val presenter by lazy { context?.let { nickname?.let { it1 -> EscortHousePresenter(it, it1) } } }

    private val locationText by lazy { findViewById<TextView>(R.id.locationText) }

    private var nickname: String? = null

    override fun onViewCreate(savedInstanceState: Bundle?) {

        activity?.let { presenter?.setMapView(mapView, it, savedInstanceState, locationText) }
    }

    override fun onInflated(savedInstanceState: Bundle?) {
        presenter?.setBarChart(barChartView)
        presenter?.setStepCount(stepCount)
        changeOlder.setOnClickListener {
            activity?.let { it1 -> presenter?.changeOlder(mapView, it1, savedInstanceState, locationText) }
        }
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

    override fun getLayoutResId(): Int {
        return R.layout.house_fragment
    }

    fun setNickName(name: String) {
        nickname = name
    }
}
