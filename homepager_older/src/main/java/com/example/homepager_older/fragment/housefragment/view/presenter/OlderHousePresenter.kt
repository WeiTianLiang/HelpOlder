package com.example.homepager_older.fragment.housefragment.view.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import com.example.homepager_older.fragment.housefragment.view.model.MedicineModel
import com.example.homepager_older.fragment.housefragment.view.model.StepModel
import com.example.homepager_older.fragment.minefragment.model.OlderDataModel
import com.example.homepager_older.fragment.minefragment.presenter.GetOlderInterface
import com.example.tools.model.BaseStringModel
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import com.example.tools.net.PackageGson
import com.example.tools.tool.listMax
import com.example.tools.view.BarChartView
import com.example.tools.view.BaseMapView
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @author WeiTianLiang
 */
class OlderHousePresenter(
    private val context: Context,
    private val nickname: String
) : OlderHouseInterface {

    /**
     * 柱状图数据
     */
    private val listData = ArrayList<Int>()
    private val xList = ArrayList<String>()

    private val request =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetOlderHouseInterface::class.java)

    private val request1 =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetOlderInterface::class.java)

    private var id = -1
    // 当前纬度
    private var latitude: Double? = null
    // 当前经度
    private var longitude: Double? = null

    private val timer = Timer(true)
    private var mapView: BaseMapView? = null
    private var activity: Activity? = null
    private var locationText: TextView? = null
    private var savedInstanceState: Bundle? = null

    @SuppressLint("SimpleDateFormat")
    private val dft = SimpleDateFormat("MM月dd")

    /**
     * 地图
     */
    override fun setMapView(
        mapView1: BaseMapView,
        activity1: Activity,
        savedInstanceState1: Bundle?,
        locationText1: TextView
    ) {
        val call1 = request1.getOlderData(nickname)
        call1.enqueue(object : Callback<OlderDataModel> {
            override fun onResponse(call: Call<OlderDataModel>, response: Response<OlderDataModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        id = response.body()!!.data?.id!!
                    }
                }
            }

            override fun onFailure(call: Call<OlderDataModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })

        mapView1.onCreate(savedInstanceState1, locationText1)
        mapView1.showNowLocation(activity1)

        mapView = mapView1
        activity = activity1
        locationText = locationText1
        savedInstanceState = savedInstanceState1

        timer.schedule(task, 5000, 3000)
    }

    override fun setStep(stepCount: TextView) {
        val call = request.getOlderStep(nickname)
        call.enqueue(object : Callback<StepModel> {
            override fun onResponse(call: Call<StepModel>, response: Response<StepModel>) {
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
                        stepCount.text = max.toString()
                        getStepCount?.getStep(max)
                    }
                }
            }

            override fun onFailure(call: Call<StepModel>, t: Throwable) {

            }
        })
    }

    private var getStepCount: OnGetStepCount? = null

    interface OnGetStepCount {
        fun getStep(count: Int)
    }

    fun setOnGetStepCount(getStepCount: OnGetStepCount) {
        this.getStepCount = getStepCount
    }

    private var task: TimerTask = object : TimerTask() {
        override fun run() {
            mhander.sendEmptyMessage(1)
        }
    }

    private val mhander = Handler(Handler.Callback { p0 ->
        if (p0?.what == 1) {
            activity?.let { mapView?.showNowLocation(it) }
            longitude = mapView?.getLongitude()
            latitude = mapView?.getLatitude()

            val map = HashMap<Any, Any>()
            map["position"] = "$latitude $longitude"
            map["id"] = id
            val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
            val call = request.putParentMap(body)
            call.enqueue(object : Callback<BaseStringModel> {
                override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.code == "200") {

                        }
                    }
                }

                override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {
                    Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
                }
            })
        }
        false
    })

    override fun setBarChart(barChartView: BarChartView) {
        val call = request.getOlderStep(nickname)
        call.enqueue(object : Callback<StepModel> {
            override fun onResponse(call: Call<StepModel>, response: Response<StepModel>) {
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

            override fun onFailure(call: Call<StepModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun setHealthy(textView: TextView) {
        val call = request1.getOlderData(nickname)
        call.enqueue(object : Callback<OlderDataModel> {
            override fun onResponse(call: Call<OlderDataModel>, response: Response<OlderDataModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        if (response.body()!!.data?.healthStatus == 0) {
                            textView.text = "良好！请继续保持"
                        } else {
                            textView.text = "请注意身体"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<OlderDataModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun setMedicine(textView: TextView) {
        val call = request.getMedicine(nickname)
        call.enqueue(object : Callback<MedicineModel> {
            override fun onResponse(call: Call<MedicineModel>, response: Response<MedicineModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {

                    }
                }
            }

            override fun onFailure(call: Call<MedicineModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
