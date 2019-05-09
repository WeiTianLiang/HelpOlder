package com.example.entrance.register.presenter

import android.support.v4.app.Fragment

/**
 * 注册接口
 * @author weitianliang
 */
interface IRegisterPresenter {

    /**
     * 返回接口
     */
    fun doBack()

    /**
     * 注册执行接口
     */
    fun doRegister(type: String,
                   name: String,
                   age: String,
                   sex: String,
                   account: String,
                   password: String,
                   repeat: String,
                   imageUrl: String,
                   state: Int = 0,
                   workTime: String = "",
                   workExperience: String = "",
                   type_work: Int = 0)

    /**
     * 获取头像
     */
    fun doGetHeadPicture(fragment: Fragment)

}