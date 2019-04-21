package com.example.entrance.register.presenter

import android.app.Activity
import android.content.Context
import com.example.entrance.register.view.RegisterActivity
import com.example.tools.activity.finishWithAnimal
import android.content.Intent
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.Toast
import com.example.tools.activity.doGetPicture
import com.example.tools.activity.jumpActivity

/**
 *
 * @author weitianliang
 */
class RegisterPresenterCompl(private val context: Context) : IRegisterPresenter {

    override fun doRegister(
        type: String,
        name: String,
        age: String,
        sex: String,
        account: String,
        password: String,
        repeat: String
    ) {
        if (type == "" || name == "" || age == "" || sex == "" || account == "" || password == "" || repeat == "") {
            Toast.makeText(context, "请填写所有数据", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != repeat) {
            Toast.makeText(context, "请输入正确的密码", Toast.LENGTH_SHORT).show()
            return
        }

        when (type) {
            "老人" -> jumpActivity("/homepager_older/OlderActivity", context as Activity)
            "子女" -> jumpActivity("/homepager_children/ChildrenActivity", context as Activity)
            "陪护" -> jumpActivity("/homepager_escort/EscortActivity", context as Activity)
            else -> Toast.makeText(context, "登陆失败!!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun doGetHeadPicture() {
        doGetPicture(context as RegisterActivity)
    }

    override fun doBack() {
        finishWithAnimal(context as RegisterActivity)
    }
}
