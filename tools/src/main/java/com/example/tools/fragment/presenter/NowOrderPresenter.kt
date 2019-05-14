package com.example.tools.fragment.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.example.tools.dialog.ReleaseDialog
import com.example.tools.fragment.SaveOrder
import com.example.tools.fragment.interfaces.GetIssueInterface
import com.example.tools.fragment.model.OrderModel
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import com.example.tools.net.PackageGson
import com.example.tools.view.BaseMapView
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 *
 * @author WeiTianLiang
 */
@SuppressLint("SimpleDateFormat")
class NowOrderPresenter(
    val context: Context,
    val nickname: String
) : NowOrderInterface {

    private val releaseDialog by lazy { ReleaseDialog(context) }

    val request =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetIssueInterface::class.java)

    val simp by lazy { SimpleDateFormat("yyyy-MM-dd hh:mm") }

    private val timer = Timer(true)

    private var isOver: TextView? = null
    private var escortName: TextView? = null
    private var escort_ID: TextView? = null
    private var escort_time: TextView? = null
    private var escort_type: TextView? = null


    private var mapView: BaseMapView? = null
    private var activity: Activity? = null
    private var savedInstanceState: Bundle? = null

    override fun setData(
        isOver: TextView,
        escortName: TextView,
        escort_ID: TextView,
        escort_time: TextView,
        escort_type: TextView
    ) {

        this.isOver = isOver
        this.escortName = escortName
        this.escort_ID = escort_ID
        this.escort_time = escort_time
        this.escort_type = escort_type

        timer.schedule(task1, 100, 5000)
    }

    private var task1: TimerTask = object : TimerTask() {
        override fun run() {
            hander.sendEmptyMessage(2)
        }
    }

    private val hander = Handler(Handler.Callback { p0 ->
        if (p0?.what == 2) {
            val call = SaveOrder.readFile(context)?.let { request.getOrderWithOrderNo(it) }
            call?.enqueue(object : Callback<OrderModel> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.code == "200") {
                            isOver?.text = if (response.body()!!.data?.orderStatus == 0) {
                                escortName?.text = "无"
                                "还没有人接单"
                            } else {
                                "陪护员已经接单"
                            }
                            escort_ID?.text = response.body()!!.data?.orderNo
                            escort_time?.text =
                                response.body()!!.data?.escortStart?.let { simp.format(Date(it)) }.toString() + " 到 " + response.body()!!.data?.escortEnd?.let {
                                    simp.format(Date(it))
                                }.toString()
                            escort_type?.text = if (response.body()!!.data?.escortType == 0) {
                                "临时陪护"
                            } else {
                                "长期陪护"
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                    Toast.makeText(context, "网络有问题!!!", Toast.LENGTH_SHORT).show()
                }
            })
        }
        false
    })

    override fun setMapView(
        mapView1: BaseMapView,
        activity1: Activity,
        savedInstanceState1: Bundle?
    ) {
        mapView1.onCreate(savedInstanceState1, null)
        mapView1.showNowLocation(activity1)

        mapView = mapView1
        activity = activity1
        savedInstanceState = savedInstanceState1

        timer.schedule(task, 100, 3000)
    }

    private var task: TimerTask = object : TimerTask() {
        override fun run() {
            mhander.sendEmptyMessage(1)
        }
    }

    private val mhander = Handler(Handler.Callback { p0 ->
        if (p0?.what == 1) {
            activity?.let { mapView?.showNowLocation(it) }
        }
        false
    })

    override fun showDetail() {
        ARouter.getInstance()
            .build("/tools/DetailActivity")
            .withInt("key", 100)
            .withString("orderNo", SaveOrder.readFile(context))
            .withString("nickname", nickname)
            .navigation()
    }

    override fun orderPush() {
        releaseDialog.setCanceledOnTouchOutside(false)
        releaseDialog.window?.setGravity(Gravity.CENTER)
        releaseDialog.show()
        releaseDialog.setOnClick(object : ReleaseDialog.OnClick {
            override fun cancelClick() {
                releaseDialog.cancel()
            }

            override fun sureClick(
                location: String,
                manState: Int,
                manName: String,
                healthy: Int,
                state: Int,
                startTime: Long,
                endTime: Long,
                other: String
            ) {
                releaseDialog.cancel()
                // 数据传到服务器
                val map = HashMap<Any, Any>()
                map["address"] = location
                map["desc"] = other
                map["emergencyStatus"] = manState
                map["escortEnd"] = endTime
                map["escortStart"] = startTime
                map["escortType"] = state
                map["healthStatus"] = healthy
                map["parentEscort"] = nickname
                map["position"] = ""
                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                val call = request.postOrderSave(body)
                call.enqueue(object : Callback<OrderModel> {
                    override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show()
                                response.body()!!.data?.orderNo?.let { SaveOrder.writeFile(it, context) }
                                handler.sendEmptyMessage(1)
                            } else {
                                Toast.makeText(context, "发布失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                        Toast.makeText(context, "网络有问题!!!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })
    }

    private val handler = Handler(Handler.Callback { message ->
        if (message.what == 1) {
            val call = SaveOrder.readFile(context)?.let { request.getOrderWithOrderNo(it) }
            call?.enqueue(object : Callback<OrderModel> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.code == "200") {
                            isOver?.text = if (response.body()!!.data?.orderStatus == 0) {
                                escortName?.text = "无"
                                "还没有人接单"
                            } else {
                                "陪护员已经接单"
                            }
                            escort_ID?.text = response.body()!!.data?.orderNo
                            escort_time?.text =
                                response.body()!!.data?.escortStart?.let { simp.format(Date(it)) } + " 到 " + response.body()!!.data?.escortEnd?.let {
                                    simp.format(Date(it))
                                }
                            escort_type?.text = if (response.body()!!.data?.escortType == 0) {
                                "临时陪护"
                            } else {
                                "长期陪护"
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                    Toast.makeText(context, "网络有问题!!!", Toast.LENGTH_SHORT).show()
                }
            })
        }
        false
    })
}