package com.example.entrance.login.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.RadioButton
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.entrance.R
import com.example.entrance.login.presenter.LoginPresenterCompl
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.logintext.*


@Suppress("CAST_NEVER_SUCCEEDS")
@Route(path = "/login/LoginActivity")
class LoginActivity : AppCompatActivity() {

    private val presenter = LoginPresenterCompl(this)
    private var identity_str = ""
    private var identity_button: RadioButton? = null
    private val myReceiver by lazy { MyReceiver() }
    private val intentFilter = IntentFilter("com.example.entrance.register.presenter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        main_login.setOnClickListener {
            presenter.doLogin(input_account.text.toString(),
                input_password.text.toString(), identity_str)
        }

        to_register.setOnClickListener {
            presenter.doRegister()
        }

        identity.setOnCheckedChangeListener { _, checkedId ->
            identity_button = findViewById(checkedId)
            identity_str = identity_button?.text.toString()
        }

        registerReceiver(myReceiver, intentFilter)
    }

    private class MyReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            (p0 as LoginActivity).finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiver)
    }
}
