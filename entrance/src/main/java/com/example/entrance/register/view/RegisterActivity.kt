package com.example.entrance.register.view

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.entrance.R
import com.example.tools.activity.addFragment
import com.example.tools.activity.replaceFragment
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val childrenRegisterFragment by lazy { ChildrenRegisterFragment() }
    private val olderRegisterFragment by lazy { OlderRegisterFragment() }
    private val escortRegisterFragment by lazy { EscortRegisterFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        addFragment(olderRegisterFragment, R.id.register_fragment)
        register_olderFragment.setTextColor(Color.GRAY)

        register_olderFragment.setOnClickListener {
            replaceFragment(olderRegisterFragment, R.id.register_fragment)
            register_olderFragment.setTextColor(Color.GRAY)
            register_childrenFragment.setTextColor(Color.BLACK)
            register_escortFragment.setTextColor(Color.BLACK)
        }

        register_childrenFragment.setOnClickListener {
            replaceFragment(childrenRegisterFragment, R.id.register_fragment)
            register_olderFragment.setTextColor(Color.BLACK)
            register_childrenFragment.setTextColor(Color.GRAY)
            register_escortFragment.setTextColor(Color.BLACK)
        }

        register_escortFragment.setOnClickListener {
            replaceFragment(escortRegisterFragment, R.id.register_fragment)
            register_olderFragment.setTextColor(Color.BLACK)
            register_childrenFragment.setTextColor(Color.BLACK)
            register_escortFragment.setTextColor(Color.GRAY)
        }
    }
}
