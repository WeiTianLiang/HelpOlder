package com.example.homepager_older.fragment.housefragment.view.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MarkerOptions
import com.amap.api.maps2d.model.MyLocationStyle
import com.example.homepager_older.R
import com.example.homepager_older.step.BindService
import com.example.tools.fragment.BaseFragment
import com.example.tools.view.initBarChartView
import com.example.tools.view.showBarChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.older_house_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * 老人 - 首页 fragment
 * @author weitianliang
 */
class HouseFragment : BaseFragment(), OnChartValueSelectedListener, LocationSource, AMapLocationListener {

    /**
     * 柱状图数据
     */
    private val listData = ArrayList<Int>()
    private val xList = ArrayList<String>()
    /**
     * 地图
     */
    private val aMap by lazy { mapView.map }
    /**
     * 位置定位
     */
    private val mLocationClient by lazy { AMapLocationClient(context) }
    private val mLocationOption by lazy { AMapLocationClientOption() }
    private var isFirstLoc = true
    private var mListener: LocationSource.OnLocationChangedListener? = null
    private val mapView by lazy { findViewById<MapView>(R.id.mapView) }
    /**
     * 当前步数
     */
    private var step = 0

    /**
     * 城市信息
     */
    private var youDistrict: String? = null
    /**
     * 街道信息
     */
    private var youStreetNum: String? = null

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

    override fun onViewCreate(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        init()
        showNowLocation()
//        showOtherLocation("108.967945","34.345741")
        // 启动计步
        val intent = Intent(activity?.applicationContext, BindService::class.java)
        activity?.applicationContext?.let {
            isBind = it.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        activity?.applicationContext?.startService(intent)
    }

    override fun onInflated(savedInstanceState: Bundle?) {
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
        initBarChartView(barChart, xList)
        showBarChart(barChart, listData, "过去五天步数", Color.BLUE)
    }

    override fun getLayoutResId(): Int {
        return R.layout.older_house_fragment
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

    override fun onNothingSelected() {
    }

    private fun init() {
        aMap.isTrafficEnabled = true

        // 设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17f))
        aMap.setLocationSource(this)
        val setting = aMap.uiSettings
        setting.isMyLocationButtonEnabled = true
        aMap.isMyLocationEnabled = true
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
                message.arg1 = if(step >= stepCount) {
                    step
                } else {
                    // 重启服务步数重新计算，做加法运算
                    step += stepCount
                    step
                }
                handler.sendMessage(message)
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
        }
    }

    // 定位当前位置
    private fun showNowLocation() {

        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location1))
        myLocationStyle.radiusFillColor(android.R.color.transparent)
        myLocationStyle.strokeColor(android.R.color.transparent)
        aMap.setMyLocationStyle(myLocationStyle)

        if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) }
            != PackageManager.PERMISSION_GRANTED
        ) {
            //申请WRITE_EXTERNAL_STORAGE权限
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    0
                )
            }//自定义的code
        } else if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            //申请WRITE_EXTERNAL_STORAGE权限
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    0
                )
            }//自定义的code
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(this)
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.isNeedAddress = true
        //设置是否只定位一次,默认为false
        mLocationOption.isOnceLocation = false
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.isWifiActiveScan = true
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.isMockEnable = false
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.interval = 2000
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption)
        //启动定位
        mLocationClient.startLocation()
    }

    // 定位他人位置
    private fun showOtherLocation(lng: String?, lat: String?) {
        if (lng.isNullOrEmpty() || lat.isNullOrEmpty()) {
            return
        }
        val latLng = LatLng(lat.toDouble(), lng.toDouble())
        val cu = CameraUpdateFactory.changeLatLng(latLng)
        aMap.moveCamera(cu)
        // 创建MarkerOptions对象
        val markerOptions = MarkerOptions()
        // 设置MarkerOptions的添加位置
        markerOptions.position(latLng)
        // 设置MarkerOptions的图标
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        markerOptions.draggable(true)
        // 添加MarkerOptions（实际上就是添加Marker）
        val marker = aMap.addMarker(markerOptions)
        // 设置默认显示的信息窗
        marker.showInfoWindow()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onLocationChanged(amapLocation: AMapLocation?) {

        if (amapLocation != null) {
            //定位成功回调信息，设置相关消息
            if (amapLocation.errorCode == 0) {
                amapLocation.locationType //获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.latitude //获取纬度
                amapLocation.longitude //获取经度
                amapLocation.accuracy //获取精度信息
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date = Date(amapLocation.time)
                df.format(date) //定位时间
                amapLocation.address // 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.country // 国家信息
                amapLocation.province // 省信息
                amapLocation.city // 城市信息
                youDistrict = amapLocation.district // 城区信息
                youStreetNum = amapLocation.street // 街道信息
                amapLocation.streetNum // 街道门牌号信息
                amapLocation.cityCode // 城市编码
                amapLocation.adCode

                if (!youStreetNum.isNullOrEmpty() || !youDistrict.isNullOrEmpty()) {
                    locationText.text = "$youDistrict,$youStreetNum"
                }

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //将地图移动到定位点
                    aMap.moveCamera(
                        CameraUpdateFactory.changeLatLng(
                            LatLng(
                                amapLocation.latitude,
                                amapLocation.longitude
                            )
                        )
                    )
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener?.onLocationChanged(amapLocation)
                    //获取定位信息
//                    val buffer = StringBuffer()
//                    buffer.append(
//                        amapLocation.country + "" + amapLocation.province
//                                + "" + amapLocation.city + "" + amapLocation.province
//                                + "" + amapLocation.district + "" + amapLocation.street
//                                + "" + amapLocation.streetNum
//                    )
//                    Toast.makeText(activity?.applicationContext, buffer.toString(), Toast.LENGTH_LONG).show()
                    isFirstLoc = false
                }
            } else {
            }
        }
    }

    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
        mListener = p0
    }

    override fun deactivate() {
        mListener = null
    }
}
