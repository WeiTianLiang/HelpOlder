package com.example.homepager_escort.fragment.housefragment.view.presenter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.homepager_escort.fragment.minefragment.view.model.EscortParentModel
import com.example.homepager_escort.fragment.minefragment.view.presenter.GetEscortInterface
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import com.example.tools.view.BarChartView
import com.example.tools.view.BaseMapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author WeiTianLiang
 */
class EscortHousePresenter(
    private val context: Context,
    private val nickname: String
) : EscortHouseInterface {

    /**
     * 柱状图数据
     */
    private val listData = ArrayList<Int>()
    private val xList = ArrayList<String>()

    private val timer = Timer(true)
    private var mapView: BaseMapView? = null
    private var activity: Activity? = null
    private var locationText: TextView? = null
    private var savedInstanceState: Bundle? = null

    private val dft = SimpleDateFormat("MM月dd")

    private val request =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetEscortInterface::class.java)

    override fun changeOlder(
        mapView: BaseMapView,
        activity: Activity,
        savedInstanceState: Bundle?,
        locationText: TextView
    ) {
        mapView.onCreate(savedInstanceState, locationText)
        mapView.showOtherLocation("104.967945","39.345741")
    }

    override fun setStepCount(steCount: TextView) {
        steCount.text = "12312"
    }

    override fun setMapView(
        mapView1: BaseMapView,
        activity1: Activity,
        savedInstanceState1: Bundle?,
        locationText1: TextView
    ) {
        mapView1.onCreate(savedInstanceState1, locationText1)
        mapView = mapView1
        activity = activity1
        savedInstanceState = savedInstanceState1
        locationText = locationText1
        timer.schedule(task, 100, 3000)
    }

    private var task: TimerTask = object : TimerTask() {
        override fun run() {
            mhander.sendEmptyMessage(1)
        }
    }

    private val mhander= Handler(Handler.Callback { p0 ->
        if(p0?.what == 1) {
            val call = request.getOlderData(nickname)
            call.enqueue(object : Callback<EscortParentModel> {
                override fun onResponse(call: Call<EscortParentModel>, response: Response<EscortParentModel>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.code == "200") {
                            mapView?.showOtherLocation(response.body()!!.data?.parentList?.get(0)?.position?.split(" ")?.get(1),
                                response.body()!!.data?.parentList?.get(0)?.position?.split(" ")?.get(0)
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<EscortParentModel>, t: Throwable) {

                }
            })
        }
        false
    })

    override fun setBarChart(barChartView: BarChartView) {
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
        barChartView.initBarChartView(xList)
        barChartView.showBarChart(listData, "过去五天步数", Color.BLUE)
    }
}