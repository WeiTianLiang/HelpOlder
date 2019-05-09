package com.example.entrance.login.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.entrance.R
import com.example.entrance.register.view.RegisterActivity
import com.example.tools.activity.jumpActivity
import com.example.tools.model.BaseModel
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 登陆业务逻辑层
 * 接口功能实现
 * Created by WTL on 2018/6/4.
 */

@Suppress("UNUSED_EXPRESSION")
class LoginPresenterCompl(private val context: Context) : ILoginPresenter {

    private var call: Call<BaseModel>? = null

    override fun doLogin(account: String, password: String, identity: String) {
        if (account == "" || password == "" || identity == "") {
            Toast.makeText(context, "登陆失败!!!", Toast.LENGTH_SHORT).show()
            return
        }
        val request =
            CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetLoginInterface::class.java)
        when (identity) {
            "老人" -> {
                call = request.parentLogin(account, password)
                call?.enqueue(object : Callback<BaseModel> {
                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                        if(response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                response.body()!!.data?.id?.let {
                                    jumpActivity("/homepager_older/OlderActivity", context as Activity, account,
                                        it
                                    )
                                }
                            } else {
                                Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                        Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
                    }

                })
            }
            "陪护" -> {
                call = request.escortLogin(account, password)
                call?.enqueue(object : Callback<BaseModel> {
                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                        if(response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                response.body()!!.data?.id?.let {
                                    jumpActivity("/homepager_escort/EscortActivity", context as Activity, account,
                                        it
                                    )
                                }
                            } else {
                                Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                        Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
                    }

                })
            }
            "子女" -> {
                call = request.childrenLogin(account, password)
                call = request.escortLogin(account, password)
                call?.enqueue(object : Callback<BaseModel> {
                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                        if(response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                response.body()!!.data?.id?.let {
                                    jumpActivity("/homepager_children/ChildrenActivity", context as Activity, account,
                                        it
                                    )
                                }
                            } else {
                                Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                        Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }

    override fun doRegister() {
        val intent = Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
        (context as Activity).overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out)
    }
}
