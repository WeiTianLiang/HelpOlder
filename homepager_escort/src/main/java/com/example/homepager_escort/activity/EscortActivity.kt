package com.example.homepager_escort.activity

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.homepager_escort.fragment.escortfragment.view.view.EscortFragment
import com.example.homepager_escort.fragment.housefragment.view.view.HouseFragment
import com.example.homepager_escort.fragment.minefragment.view.view.MineFragment
import com.example.tools.FallDeteation.CreateXYZ
import com.example.tools.activity.BaseActivity

@Route(path = "/homepager_escort/EscortActivity")
class EscortActivity : BaseActivity() {

    var nickname: String? = null

    private val escortFragment by lazy { EscortFragment() }
    private val houseFragment by lazy { HouseFragment() }
    private val mineFragment by lazy { MineFragment() }
    private val fragmentList = arrayListOf<Fragment>()
    private val createXYZ by lazy { CreateXYZ(this, "escort") }

    override fun onBaseCreate() {
        nickname = intent.getStringExtra("nickname")
        escortFragment.setNickName(nickname!!)
        houseFragment.setNickName(nickname!!)
        mineFragment.setNickName(nickname!!)
        createXYZ.onCreate(this)
    }

    override fun getFragmentList(): ArrayList<Fragment> {
        fragmentList.add(houseFragment)
        fragmentList.add(escortFragment)
        fragmentList.add(mineFragment)
        return fragmentList
    }

    override fun onPause() {
        super.onPause()
        createXYZ.onPause()
    }

    override fun onResume() {
        super.onResume()
        createXYZ.onResume()
    }
}
