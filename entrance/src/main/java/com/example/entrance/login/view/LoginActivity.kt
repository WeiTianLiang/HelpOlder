package com.example.entrance.login.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.entrance.R
import com.example.entrance.login.presenter.LoginPresenterCompl
import kotlinx.android.synthetic.main.logintext.*
import kotlinx.android.synthetic.main.activity_login.*


@Suppress("CAST_NEVER_SUCCEEDS")
@Route(path = "/login/LoginActivity")
class LoginActivity : AppCompatActivity() {

    private val presenter = LoginPresenterCompl(this)
    private var identity_str = ""
    private var identity_button: RadioButton? = null

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

    }
}
