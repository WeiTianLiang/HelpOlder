package com.example.homepager_older.activity

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.homepager_older.fragment.escortfragment.view.view.EscortFragment
import com.example.homepager_older.fragment.housefragment.view.view.HouseFragment
import com.example.homepager_older.fragment.minefragment.view.MineFragment
import com.example.tools.activity.BaseActivity
import com.example.tools.onrpxactivity.KeepLiveManager

@Route(path = "/homepager_older/OlderActivity")
class OlderActivity : BaseActivity() {

    private val escortFragment by lazy { EscortFragment() }
    private val houseFragment by lazy { HouseFragment() }
    private val mineFragment by lazy { MineFragment() }
    private val fragmentList = arrayListOf<Fragment>()

    override fun onBaseCreate() {
        KeepLiveManager(this).registerBroadCast(this)
    }

    override fun getFragmentList(): ArrayList<Fragment> {
        fragmentList.add(houseFragment)
        fragmentList.add(escortFragment)
        fragmentList.add(mineFragment)
        return fragmentList
    }

    override fun onDestroy() {
        super.onDestroy()
        KeepLiveManager(this).unRegisterBroadCast(this)
    }
}
