package com.example.entrance.register.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast
import com.example.entrance.register.view.RegisterActivity
import com.example.tools.activity.doGetPicture
import com.example.tools.activity.finishWithAnimal
import com.example.tools.activity.jumpActivity
import com.example.tools.model.BaseModel
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import com.example.tools.net.PackageGson
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 *
 * @author weitianliang
 */
@Suppress("UNUSED_EXPRESSION")
class RegisterPresenterCompl(private val context: Context) : IRegisterPresenter {

    private var call: Call<BaseModel>? = null

    override fun doRegister(
        type: String,
        name: String,
        age: String,
        sex: String,
        account: String,
        password: String,
        repeat: String,
        imageUrl: String,
        state: Int,
        workTime: String,
        workExperience: String,
        type_work: Int
    ) {
        if (type == "" || name == "" || age == "" || sex == "" || account == "" || password == "" || repeat == "") {
            Toast.makeText(context, "请填写所有数据", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != repeat) {
            Toast.makeText(context, "请输入正确的密码", Toast.LENGTH_SHORT).show()
            return
        }

        val request =
            CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetRegisterInterface::class.java)
        when (type) {
            "老人" -> {
                call = request.parentCheck(account)
                call?.enqueue(object : Callback<BaseModel> {
                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                        if(response.isSuccessful && response.body() != null) {
                            if(response.body()!!.msg == "请求成功" && response.body()!!.code == "200") {
                                // 开始注册
                                val registerMap = HashMap<Any, Any>()
                                registerMap["type"] = type
                                registerMap["name"] = name
                                registerMap["age"] = age
                                registerMap["gender"] = sex
                                registerMap["nickname"] = account
                                registerMap["password"] = password
                                registerMap["imageUrl"] = imageUrl
                                registerMap["healthStatus"] = state
                                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(registerMap))

                                val call1 = request.createParentUser(body)
                                call1.enqueue(object : Callback<BaseModel> {
                                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                                        if(response.isSuccessful && response.body() != null) {
                                            if (response.body()!!.code == "200") {
                                                Toast.makeText(context, "注册成功!!!", Toast.LENGTH_SHORT).show()
                                                response.body()!!.data?.id?.let {
                                                    jumpActivity("/homepager_older/OlderActivity", context as Activity, account,
                                                        it
                                                    )
                                                }
                                                val intent = Intent()
                                                intent.action = "com.example.entrance.register.presenter"
                                                context.sendBroadcast(intent)
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                                        Toast.makeText(context, "注册失败!!!", Toast.LENGTH_SHORT).show()
                                    }

                                })
                            } else {
                                Toast.makeText(context, "账号已存在!!!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                        Log.e("onFailure", t.message + "失败")
                    }

                })
            }
            "子女" -> {
                call = request.childrenCheck(account)
                call?.enqueue(object : Callback<BaseModel> {
                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                        if(response.isSuccessful && response.body() != null) {
                            if(response.body()!!.msg == "请求成功" && response.body()!!.code == "200") {
                                val registerMap = HashMap<Any, Any>()
                                registerMap["type"] = type
                                registerMap["name"] = name
                                registerMap["age"] = age
                                registerMap["gender"] = sex
                                registerMap["nickname"] = account
                                registerMap["password"] = password
                                registerMap["imageUrl"] = imageUrl
                                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(registerMap))

                                val call1 = request.createChildUser(body)
                                call1.enqueue(object : Callback<BaseModel> {
                                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                                        if(response.isSuccessful && response.body() != null) {
                                            if (response.body()!!.msg == "请求成功" && response.body()!!.code == "200") {
                                                response.body()!!.data?.id?.let {
                                                    jumpActivity("/homepager_children/ChildrenActivity", context as Activity, account,
                                                        it
                                                    )
                                                }
                                                Toast.makeText(context, "注册成功!!!", Toast.LENGTH_SHORT).show()
                                                val intent = Intent()
                                                intent.action = "com.example.entrance.register.presenter"
                                                context.sendBroadcast(intent)
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                                        Toast.makeText(context, "注册失败!!!", Toast.LENGTH_SHORT).show()
                                    }

                                })
                            } else {
                                Toast.makeText(context, "账号已存在!!!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                        Log.e("onFailure", t.message + "失败")
                    }

                })
            }
            "陪护" -> {
                call = request.escortCheck(account)
                call?.enqueue(object : Callback<BaseModel> {
                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                        if(response.isSuccessful && response.body() != null) {
                            if(response.body()!!.msg == "请求成功" && response.body()!!.code == "200") {
                                val registerMap = HashMap<Any, Any>()
                                registerMap["type"] = type
                                registerMap["name"] = name
                                registerMap["age"] = age
                                registerMap["gender"] = sex
                                registerMap["nickname"] = account
                                registerMap["password"] = password
                                registerMap["imageUrl"] = imageUrl
                                registerMap["workExperience"] = workExperience
                                registerMap["workTime"] = workTime
                                registerMap["workType"] = type_work
                                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(registerMap))
                                val call1 = request.createEscortUser(body)
                                call1.enqueue(object : Callback<BaseModel> {
                                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                                        if(response.isSuccessful && response.body() != null) {
                                            if (response.body()!!.msg == "请求成功" && response.body()!!.code == "200") {
                                                response.body()!!.data?.id?.let {
                                                    jumpActivity("/homepager_escort/EscortActivity", context as Activity, account,
                                                        it
                                                    )
                                                }
                                                Toast.makeText(context, "注册成功!!!", Toast.LENGTH_SHORT).show()
                                                val intent = Intent()
                                                intent.action = "com.example.entrance.register.presenter"
                                                context.sendBroadcast(intent)
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                                        Toast.makeText(context, "注册失败!!!", Toast.LENGTH_SHORT).show()
                                    }

                                })
                            } else {
                                Toast.makeText(context, "账号已存在!!!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                        Log.e("onFailure", t.message + "失败")
                    }

                })
            }
        }
    }

    override fun doGetHeadPicture(fragment: Fragment) {
        doGetPicture(fragment = fragment)
    }

    override fun doBack() {
        finishWithAnimal(context as RegisterActivity)
    }
}
