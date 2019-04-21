package com.example.entrance.login.presenter

/**
 * 登陆业务逻辑功能接口
 * Created by WTL on 2018/6/4.
 */

interface ILoginPresenter {

    /**
     * 处理登陆接口
     */
    fun doLogin(account: String, password: String, identity: String)

    /**
     * 跳转到注册
     */
    fun doRegister()
}
