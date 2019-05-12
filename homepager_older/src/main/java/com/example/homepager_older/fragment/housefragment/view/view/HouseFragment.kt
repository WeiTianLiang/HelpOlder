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
import com.example.homepager_older.R
import com.example.homepager_older.fragment.housefragment.view.presenter.OlderHousePresenter
import com.example.homepager_older.fragment.housefragment.view.tools.SharePreferenceStep
import com.example.homepager_older.step.BindService
import com.example.tools.fragment.BaseFragment
import com.example.tools.view.BaseMapView
import kotlinx.android.synthetic.main.older_house_fragment.*

/**
 * 老人 - 首页 fragment
 * @author weitianliang
 */
class HouseFragment : BaseFragment() {

    private var nickname: String? = null
    private val stepCount by lazy { findViewById<TextView>(R.id.stepCount) }
    private val mapView by lazy { findViewById<BaseMapView>(R.id.mapView) }
    private val locationText by lazy { findViewById<TextView>(R.id.locationText) }
    /**
     * 当前步数
     */
    private var step = 0

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
