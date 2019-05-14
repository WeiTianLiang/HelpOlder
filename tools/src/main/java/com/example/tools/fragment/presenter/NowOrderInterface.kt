package com.example.tools.fragment.presenter

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.example.tools.view.BaseMapView

/**
 *
 * @author WeiTianLiang
 */
interface NowOrderInterface {

    fun orderPush()

    fun setData(isOver: TextView, escortName: TextView, escort_ID: TextView, escort_time: TextView, escort_type: TextView)

    fun setMapView(mapView1: BaseMapView, activity1: Activity, savedInstanceState1: Bundle?)

}