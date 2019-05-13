package com.example.homepager_older.fragment.housefragment.view.view

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.widget.TextView
import android.widget.Toast
import com.example.homepager_older.R
import com.example.homepager_older.fragment.housefragment.view.presenter.GetOlderHouseInterface
import com.example.homepager_older.fragment.housefragment.view.presenter.OlderHousePresenter
import com.example.homepager_older.fragment.housefragment.view.tools.SharePreferenceStep
import com.example.homepager_older.step.BindService
import com.example.tools.fragment.BaseFragment
import com.example.tools.model.BaseStringModel
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import com.example.tools.net.PackageGson
import com.example.tools.view.BaseMapView
import kotlinx.android.synthetic.main.older_house_fragment.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * 老人 - 首页 fragment
 * @author weitianliang
 */
class HouseFragment : BaseFragment() {

    private var nickname: String? = null
    private val stepCount by lazy { findViewById<TextView>(R.id.stepCount) }
    private val mapView by lazy { findViewById<BaseMapView>(R.id.mapView) }
    private val locationText by lazy { findViewById<TextView>(R.id.locationText) }
    private var nowCount = 0

    /**
     * 步数服务
     */
    private var bindService: BindService? = null
    /**
     * 是否绑定
     */
    private var isBind: Boolean = false
    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {
                stepCount.text = msg.arg1.toString()
            }
        }
    }

    private val presenter by lazy { context?.let { nickname?.let { it1 -> OlderHousePresenter(it, it1) } } }

    override fun onViewCreate(savedInstanceState: Bundle?) {
        activity?.let { presenter?.setMapView(mapView, it, savedInstanceState, locationText) }
        presenter?.setStep(stepCount)
        // 启动计步
        val intent = Intent(activity?.applicationContext, BindService::class.java)
        activity?.applicationContext?.let {
            isBind = it.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        val bindService = BindService()
        bindService.setNickName(nickname)
        activity?.applicationContext?.startService(intent)
    }

    override fun onInflated(savedInstanceState: Bundle?) {
        presenter?.setOnGetStepCount(object : OlderHousePresenter.OnGetStepCount {
            override fun getStep(count: Int) {
                nowCount = count
            }
        })
        stepCount.text = "${context?.let { SharePreferenceStep.readStep(it)}}"
        presenter?.setBarChart(barChartView)
        presenter?.setHealthy(healthy)
        presenter?.setMedicine(medicineRemind)
    }

    override fun getLayoutResId(): Int {
        return R.layout.older_house_fragment
    }

    //和绷定服务数据交换的桥梁，可以通过IBinder service获取服务的实例来调用服务的方法或者数据
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val lcBinder = service as BindService.LcBinder
            bindService = lcBinder.service
            bindService?.registerCallback { stepCount ->
                //当前接收到stepCount数据，就是最新的步数
                val message = Message.obtain()
                message.what = 1
                message.arg1 = context?.let { SharePreferenceStep.readStep(it) }!!
                context?.let { SharePreferenceStep.writeStep(stepCount, it) }
                val request =
                    CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetOlderHouseInterface::class.java)
                val map = HashMap<Any, Any>()
                map["date"] = Date().time
                map["nickname"] = nickname!!
                if(nowCount <= stepCount) {
                    map["walkCount"] = stepCount
                } else {
                    map["walkCount"] = stepCount + nowCount
                }
                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                val call = request.postOlderStep(body)
                call.enqueue(object : Callback<BaseStringModel> {
                    override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {

                    }
                })
                handler.sendMessage(message)
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
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

    fun setNickName(name: String) {
        nickname = name
    }
}
