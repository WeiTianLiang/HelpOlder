package com.example.homepager_escort.activity

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.homepager_escort.fragment.escortfragment.view.EscortFragment
import com.example.homepager_escort.fragment.housefragment.view.HouseFragment
import com.example.homepager_escort.fragment.minefragment.view.MineFragment
import com.example.tools.activity.BaseActivity

@Route(path = "/homepager_escort/EscortActivity")
class EscortActivity : BaseActivity() {

    private val escortFragment by lazy { EscortFragment() }
    private val houseFragment by lazy { HouseFragment() }
    private val mineFragment by lazy { MineFragment() }
    private val fragmentList = arrayListOf<Fragment>()

    override fun onBaseCreate() {

    }

    override fun getFragmentList(): ArrayList<Fragment> {
        fragmentList.add(houseFragment)
        fragmentList.add(escortFragment)
        fragmentList.add(mineFragment)
        return fragmentList
    }
}
