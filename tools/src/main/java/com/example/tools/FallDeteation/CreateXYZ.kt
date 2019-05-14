package com.example.tools.FallDeteation

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.example.tools.dialog.FallDialog
import com.example.tools.model.BaseStringModel
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import com.example.tools.net.PackageGson
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.text.DecimalFormat
import java.util.*

/**
 * xyz三轴加速度
 * @author WeiTianLiang
 */
class CreateXYZ(
    val context: Context,
    val type: String,
    val nickname: String? = null
) : SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null
    private var bundle: Bundle? = null
    var df = DecimalFormat("0.0000000")
    private val list = arrayListOf<Acceleration>()
    private val detection by lazy { Detection() }
    private var isDown = false

    val request =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetFallDeteationInterface::class.java)

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
                    if (list.size < 500) {
                        val acceleration = Acceleration()
                        acceleration.accx = bundle?.getDouble("x")!!
                        acceleration.accy = bundle?.getDouble("y")!!
                        acceleration.accz = bundle?.getDouble("z")!!
                        list.add(acceleration)
                    } else {
                        isDown = detection.Detect(list)
                        if (isDown) {
                            val vibrator = context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                            vibrator.vibrate(longArrayOf(1000,50,50,100,50), 1)
                            val dialog: FallDialog? = if (type != "older") {
                                FallDialog(context, "您的老人出现了状况，请及时处理", "确认")
                            } else {
                                FallDialog(context, "已向子女报警", "取消警报")
                            }
                            dialog?.window?.setGravity(Gravity.CENTER)
                            dialog?.show()
                            dialog?.setOnSureClick(object : FallDialog.OnFallClick {
                                override fun buttonClick() {
                                    dialog.cancel()
                                    vibrator.cancel()
                                }
                            })
                            val map = HashMap<Any, Any>()
                            map["nickname"] = nickname!!
                            map["date"] = Date().time
                            val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                            val call = request.postFallSave(body)
                            call.enqueue(object : retrofit2.Callback<BaseStringModel> {
                                override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                                    Toast.makeText(context, "消息已经发送", Toast.LENGTH_LONG).show()
                                }

                                override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {
                                }

                            })

                        }
                        list.clear()
                    }
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
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }

            }
        }
    }
}