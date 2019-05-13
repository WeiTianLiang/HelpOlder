package com.example.homepager_children.fragment.hosuefragment.view.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import com.example.homepager_children.fragment.hosuefragment.view.model.StepCountModel
import com.example.homepager_children.fragment.minefragment.view.model.ChildParentModel
import com.example.homepager_children.fragment.minefragment.view.presenter.GetChildInterface
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import com.example.tools.tool.listMax
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
    private val olderNickname = arrayListOf<String>()
    private var stepCount: TextView? = null

    @SuppressLint("SimpleDateFormat")
    private val dft = SimpleDateFormat("MM月dd")

    private val request =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetChildInterface::class.java)

    private val requestHouse =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetChildHouseInterface::class.java)

    override fun setBarChart(barChartView: BarChartView) {
        val call = request.getParentData(nickname)
        call.enqueue(object : Callback<ChildParentModel> {
            override fun onResponse(call: Call<ChildParentModel>, response: Response<ChildParentModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        val call1 = requestHouse.getOlderStep(response.body()!!.data?.parentList?.get(0)?.nickname!!)
                        call1.enqueue(object : Callback<StepCountModel> {
                            override fun onResponse(call: Call<StepCountModel>, response: Response<StepCountModel>) {
                                if (response.isSuccessful && response.body() != null) {
                                    if (response.body()!!.code == "200") {
                                        val beginDate = Date()
                                        val calendar = Calendar.getInstance()
                                        for (i in -5 until 0) {
                                            calendar.time = beginDate
                                            calendar.add(Calendar.DATE, i)
                                            xList.add(dft.format(calendar.time))
                                            val list = arrayListOf<Int>()
                                            for (j in 0 until response.body()!!.data?.size!!) {
                                                if (dft.format(Date(response.body()!!.data?.get(j)?.date?.toLong()!!)) == dft.format(calendar.time)) {
                                                    response.body()!!.data?.get(j)?.walkCount?.let { list.add(it) }
                                                }
                                            }
                                            val max = if (list.size == 0) {
                                                0
                                            } else {
                                                listMax(list)
                                            }
                                            listData.add(max)
                                        }
                                        barChartView.initBarChartView(xList)
                                        barChartView.showBarChart(listData, "过去五天步数", Color.GREEN)
                                    }
                                }
                            }

                            override fun onFailure(call: Call<StepCountModel>, t: Throwable) {
                                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
                            }
                        })
                        for(i in 0 until response.body()!!.data?.parentList?.size!!) {
                            response.body()!!.data?.parentList?.get(i)?.nickname?.let { olderNickname.add(it) }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ChildParentModel>, t: Throwable) {

            }
        })
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
                                response.body()!!.data?.parentList?.get(0)?.position?.split(" ")?.get(0))
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
        stepCount = steCount
        timer.schedule(task1, 100 ,60000)
    }

    private var task1: TimerTask = object : TimerTask() {
        override fun run() {
            stepHandler.sendEmptyMessage(2)
        }
    }

    private val stepHandler = Handler(Handler.Callback {message ->
        if(message.what == 2) {
            val call = request.getParentData(nickname)
            call.enqueue(object : Callback<ChildParentModel> {
                override fun onResponse(call: Call<ChildParentModel>, response: Response<ChildParentModel>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.code == "200") {
                            val call1 = requestHouse.getOlderStep(response.body()!!.data?.parentList?.get(0)?.nickname!!)
                            call1.enqueue(object : Callback<StepCountModel> {
                                override fun onResponse(call: Call<StepCountModel>, response: Response<StepCountModel>) {
                                    if (response.isSuccessful && response.body() != null) {
                                        if (response.body()!!.code == "200") {
                                            val list = arrayListOf<Int>()
                                            for (i in 0 until response.body()!!.data?.size!!) {
                                                if (dft.format(Date(response.body()!!.data?.get(i)?.date?.toLong()!!)) == dft.format(Date())) {
                                                    response.body()!!.data?.get(i)?.walkCount?.let { list.add(it) }
                                                }
                                            }
                                            val max = if (list.size == 0) {
                                                0
                                            } else {
                                                listMax(list)
                                            }
                                            stepCount?.text = max.toString()
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<StepCountModel>, t: Throwable) {

                                }
                            })
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
     * 改变老人
     */
    override fun changeOlder(
        mapView: BaseMapView,
        activity: Activity,
        savedInstanceState: Bundle?,
        locationText: TextView
    ) {

    }

    override fun setHealthy(textView: TextView) {
        val call = request.getParentData(nickname)
        call.enqueue(object : Callback<ChildParentModel> {
            override fun onResponse(call: Call<ChildParentModel>, response: Response<ChildParentModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        if (response.body()!!.data?.parentList?.get(0)?.healthStatus == 0) {
                            textView.text = "您的老人身体状况良好"
                        } else {
                            textView.text = "您的老人身体状况较差"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ChildParentModel>, t: Throwable) {

            }
        })
    }
}