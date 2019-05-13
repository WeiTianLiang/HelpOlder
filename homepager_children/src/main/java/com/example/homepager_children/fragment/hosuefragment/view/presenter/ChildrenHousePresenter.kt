package com.example.homepager_children.fragment.hosuefragment.view.presenter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.homepager_children.fragment.minefragment.view.model.ChildParentModel
import com.example.homepager_children.fragment.minefragment.view.presenter.GetChildInterface
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
class ChildrenHousePresenter(
    private val context: Context,
    private val nickname: String
) : ChildrenHouseInterface {
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
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetChildInterface::class.java)

    private val requestHouse =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(ChildrenHouseInterface::class.java)

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

    /**
     * 地图
     */
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
            val call = request.getParentData(nickname)
            call.enqueue(object : Callback<ChildParentModel> {
                override fun onResponse(call: Call<ChildParentModel>, response: Response<ChildParentModel>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.code == "200") {
                            mapView?.showOtherLocation(response.body()!!.data?.parentList?.get(0)?.position?.split(" ")?.get(1),
                                response.body()!!.data?.parentList?.get(0)?.position?.split(" ")?.get(0)
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ChildParentModel>, t: Throwable) {

                }
            })
        }
        false
    })

    /**
     * 设置步数
     */
    override fun setStepCount(steCount: TextView) {
        steCount.text = "12312"
    }

    /**
     * 改变老人
     */
    override fun changeOlder(
        mapView: BaseMapView,
        activity: Activity,
        savedInstanceState: Bundle?,
        locationText: TextView
    ) {

    }
}