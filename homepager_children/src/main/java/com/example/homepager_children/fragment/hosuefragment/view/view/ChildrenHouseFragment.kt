package com.example.homepager_children.fragment.hosuefragment.view.view

import android.os.Bundle
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

    private var nickname: String? = null

    private val mapView by lazy { findViewById<BaseMapView>(R.id.mapView) }

    private val presenter by lazy { context?.let { nickname?.let { it1 -> ChildrenHousePresenter(it, it1) } } }

    private val locationText by lazy { findViewById<TextView>(R.id.locationText) }

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

    fun setNickName(name: String) {
        nickname = name
    }

}