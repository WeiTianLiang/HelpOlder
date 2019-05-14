package com.example.tools.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.tools.R
import com.example.tools.fragment.presenter.NowOrderPresenter
import com.example.tools.view.BaseMapView
import kotlinx.android.synthetic.main.escort_isover_fragment.*

/**
 * 当前发布的订单
 * @author WeiTianLiang
 */
@SuppressLint("ValidFragment")
class NowOrderFragment : BaseFragment() {

    private val presenter by lazy { context?.let { nickname?.let { it1 -> NowOrderPresenter(it, it1) } } }

    private var nickname: String? = null

    private val mapView by lazy { findViewById<BaseMapView>(R.id.mapView) }

    override fun onViewCreate(savedInstanceState: Bundle?) {

    }

    override fun onInflated(savedInstanceState: Bundle?) {
        presenter?.setData(isOver, escortName, escort_ID, escort_time, escort_type)
        activity?.let { presenter?.setMapView(mapView, it, savedInstanceState) }
        escort_push.setOnClickListener {
            presenter?.orderPush()
        }
        escort_detail.setOnClickListener {
            presenter?.showDetail()
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.escort_isover_fragment
    }

    fun setNickname(nickname: String?) {
        this.nickname = nickname
    }
}