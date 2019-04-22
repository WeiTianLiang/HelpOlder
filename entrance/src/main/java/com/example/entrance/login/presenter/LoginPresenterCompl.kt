package com.example.entrance.login.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.entrance.R
import com.example.entrance.register.view.RegisterActivity
import com.example.tools.activity.jumpActivity

/**
 * 登陆业务逻辑层
 * 接口功能实现
 * Created by WTL on 2018/6/4.
 */

class LoginPresenterCompl(private val context: Context) : ILoginPresenter {

    override fun doLogin(account: String, password: String, identity: String) {
        if (account == "" || password == "" || identity == "") {
            Toast.makeText(context, "登陆失败!!!", Toast.LENGTH_SHORT).show()
            return
        }
        //        /*
        //         * 将数据封装成map
        //         * */
        //        Map<Object, Object> loginMap = new HashMap<>();
        //        loginMap.put("account", account);
        //        loginMap.put("password", password);
        //        /*
        //         * 创建数据body
        //         * */
        //        RequestBody body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(loginMap));
        //        /*
        //         * 向服务器发送数据
        //         * */
        //        final GetLogin_Interface request = CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetLogin_Interface.class);
        //        Call<ResultModel> call = request.postUser(body);
        /*
         * 异步网络请求
         * */
        //        call.enqueue(new Callback<ResultModel>() {
        //            @Override
        //            public void onResponse(@NonNull Call<ResultModel> call, @NonNull Response<ResultModel> response) {
        //                if (response.isSuccessful()) {
        //                    if (response.body() != null) {
        //                        if (response.body().getResult() == 200) {
        //                            Intent intent = new Intent(context, MainActivity.class);
        //                            context.startActivity(intent);
        //                            ((Activity) context).finish();
        //                            ((Activity) context).overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out);
        //                        } else {
        //                            Toast.makeText(context, "登陆失败!账号,密码,验证码错误!", Toast.LENGTH_SHORT).show();
        //                        }
        //                    } else {
        //                        Toast.makeText(context, "登陆失败!", Toast.LENGTH_SHORT).show();
        //                    }
        //                } else {
        //                    Toast.makeText(context, "登陆失败!!!", Toast.LENGTH_SHORT).show();
        //                }
        //            }
        //
        //            @Override
        //            public void onFailure(Call<ResultModel> call, Throwable t) {
        //                Log.e("onFailure", t.getMessage() + "失败");
        //            }
        //        });
        when (identity) {
            "老人" -> jumpActivity("/homepager_older/OlderActivity", context as Activity)
            "子女" -> jumpActivity("/homepager_children/ChildrenActivity", context as Activity)
            "陪护" -> jumpActivity("/homepager_escort/EscortActivity", context as Activity)
            else -> Toast.makeText(context, "登陆失败!!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun doRegister() {
        val intent = Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
        (context as Activity).overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out)
    }
}
