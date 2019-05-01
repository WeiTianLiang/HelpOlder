package com.example.homepager_older.fragment.housefragment.view.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MarkerOptions
import com.amap.api.maps2d.model.MyLocationStyle
import com.example.homepager_older.R
import com.example.tools.fragment.BaseFragment
import com.example.tools.view.initBarChartView
import com.example.tools.view.showBarChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.older_house_fragment.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 老人 - 首页 fragment
 * @author weitianliang
 */
class HouseFragment : BaseFragment(), OnChartValueSelectedListener, LocationSource, AMapLocationListener {

    private val listData = ArrayList<Int>()

    private val aMap by lazy { mapView.map }
    private val mLocationClient by lazy { AMapLocationClient(activity?.applicationContext) }
    private val mLocationOption by lazy { AMapLocationClientOption() }
    private var isFirstLoc = true
    private var mListener: LocationSource.OnLocationChangedListener? = null

    /**
     * 城市信息
     */
    private var youDistrict: String? = null
    /**
     * 街道信息
     */
    private var youStreetNum: String? = null

    override fun onViewCreate() {

    }

    override fun onInflated(savedInstanceState: Bundle?) {
        for (i in 0 until 5) {
            listData.add(i + 100)
        }
        initBarChartView(barChart)
        showBarChart(barChart, listData, "步数", Color.BLUE)

        mapView.onCreate(savedInstanceState)

        init()

        showNowLocation()
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
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16f))
        aMap.setLocationSource(this)
        val setting = aMap.uiSettings
        setting.isMyLocationButtonEnabled = true
        aMap.isMyLocationEnabled = true
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
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
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

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onLocationChanged(amapLocation: AMapLocation?) {

        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.locationType
                //获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.latitude
                //获取纬度
                amapLocation.longitude
                //获取经度
                amapLocation.accuracy
                //获取精度信息
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date = Date(amapLocation.time)
                df.format(date)
                //定位时间
                amapLocation.address
                // 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.country
                // 国家信息
                amapLocation.province
                // 省信息
                amapLocation.city
                // 城市信息
                youDistrict = amapLocation.district
                // 城区信息
                amapLocation.street
                // 街道信息
                youStreetNum = amapLocation.streetNum
                // 街道门牌号信息
                amapLocation.cityCode
                // 城市编码
                amapLocation.adCode

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
                    val buffer = StringBuffer()
                    buffer.append(
                        amapLocation.country + "" + amapLocation.province
                                + "" + amapLocation.city + "" + amapLocation.province
                                + "" + amapLocation.district + "" + amapLocation.street
                                + "" + amapLocation.streetNum
                    )
                    Toast.makeText(activity?.applicationContext, buffer.toString(), Toast.LENGTH_LONG).show()
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
