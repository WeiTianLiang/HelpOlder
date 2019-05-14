package com.example.tools.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.tools.R
import com.example.tools.fragment.interfaces.GetIssueInterface
import com.example.tools.fragment.model.OrderModel
import com.example.tools.model.BaseStringModel
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import com.example.tools.net.PackageGson
import kotlinx.android.synthetic.main.activity_detail.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Route(path = "/tools/DetailActivity")
class DetailActivity : AppCompatActivity() {

    val simp by lazy { SimpleDateFormat("yyyy-MM-dd hh:mm") }

    var key = 0
    var orderNo: String? = null
    var nickname: String? = null
    private var orderId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ARouter.getInstance().inject(this)

        key = intent.getIntExtra("key", 0)
        orderNo = intent.getStringExtra("orderNo")
        nickname = intent.getStringExtra("nickname")
        orderId = intent.getIntExtra("id", 0)

        setDate()

        back.setOnClickListener {
            finish()
        }

        doIt.visibility = if(key == 1) {
            View.VISIBLE
        } else {
            View.GONE
        }

        // 接受订单
        doIt.setOnClickListener {
            val request =
                CreateRetrofit.requestRetrofit(FileOperate.readFile(this)).create(GetIssueInterface::class.java)
            val call = request.putWithEscort(nickname!!, orderId)
            call.enqueue(object : Callback<BaseStringModel>{
                override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.code == "200") {
                            val call1 = request.putOrderStatus(1, orderId)
                            call1.enqueue(object : Callback<BaseStringModel> {
                                override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                                    if (response.isSuccessful && response.body() != null && response.body()!!.code == "200") {
                                        Toast.makeText(baseContext, "成功接收订单", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {
                                }
                            })

                            val call2 = request.getOrderWithOrderNo(orderNo!!)
                            call2.enqueue(object : Callback<OrderModel> {
                                override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                                    if (response.isSuccessful && response.body() != null) {
                                        if (response.body()!!.code == "200") {
                                            val parentName = response.body()!!.data?.parentEscort
                                            val escortName = response.body()!!.data?.escortName
                                            val map = HashMap<Any, Any?>()
                                            map["escort"] = escortName
                                            map["parent"] = parentName
                                            map["status"] = 1
                                            val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                                            val call3 = request.postEscortDate(body)
                                            call3.enqueue(object : Callback<BaseStringModel> {
                                                override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                                                    if (response.isSuccessful && response.body() != null) {
                                                        if (response.body()!!.code == "200") {
                                                            Toast.makeText(baseContext, "成功与老人绑定", Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                }

                                                override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {

                                                }
                                            })
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<OrderModel>, t: Throwable) {

                                }
                            })
                        }
                    }
                }

                override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {
                    Toast.makeText(baseContext, "网络有问题!!!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun setDate() {
        val request =
            CreateRetrofit.requestRetrofit(FileOperate.readFile(this)).create(GetIssueInterface::class.java)
        val call = orderNo?.let { request.getOrderWithOrderNo(it) }
        call?.enqueue(object : Callback<OrderModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        orderNumber.text = response.body()!!.data?.orderNo
                        orderLocation.text = response.body()!!.data?.address
                        orderName.text = response.body()!!.data?.parentName
                        orderAge.text = response.body()!!.data?.parentAge.toString()
                        orderSex.text = response.body()!!.data?.parentGender
                        orderState.text = if(response.body()!!.data?.healthStatus == 0) {
                            "健康"
                        } else {
                            "不健康"
                        }
                        orderTime.text = response.body()!!.data?.escortStart?.let { simp.format(
                            Date(it)
                        ) }.toString() + " 到 " + response.body()!!.data?.escortEnd?.let { simp.format(
                            Date(it)
                        ) }.toString()
                        escortType.text = if(response.body()!!.data?.escortType == 0) {
                            "临时陪护"
                        } else {
                            "长期陪护"
                        }
                        emergency.text = if(response.body()!!.data?.emergencyStatus == 0) {
                            "紧急陪护"
                        } else {
                            "普通陪护"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                Toast.makeText(baseContext, "网络有问题!!!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
