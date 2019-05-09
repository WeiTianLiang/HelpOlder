package com.example.tools.view

import android.annotation.SuppressLint
import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.text.DecimalFormat

/**
 * xyz三轴加速度
 * @author WeiTianLiang
 */
class getXYZ : SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null
    private var bundle: Bundle? = null
    var df = DecimalFormat("0.0000000")

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
                    Log.i(
                        "三轴加速数据---->",
                        "X = " + bundle?.getDouble("x") + ", Y = " + bundle?.getDouble("y") + ", Z = " + bundle?.getDouble(
                            "z"
                        )
                    )
                }
            }
        }
    }

    fun onCreate(activity: Activity) {
        mSensorManager = activity.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        bundle = Bundle()

        Thread(GameThread()).start()
    }

    fun onResume() {
        mSensorManager?.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun onPause() {
        mSensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val x = event?.values?.get(0)?.toDouble()
        val y = event?.values?.get(1)?.toDouble()
        val z = event?.values?.get(2)?.toDouble()

        bundle?.putDouble("x", df.format(x).toDouble())
        bundle?.putDouble("y", df.format(y).toDouble())
        bundle?.putDouble("z", df.format(z).toDouble())
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    internal inner class GameThread : Runnable {
        override fun run() {
            while (!Thread.currentThread().isInterrupted) {
                val message = Message()
                message.what = 1
                // 发送消息
                mHandler.sendMessage(message)
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }

            }
        }
    }
}