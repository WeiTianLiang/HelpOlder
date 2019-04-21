package com.example.homepager_older.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.homepager_older.fragment.escortfragment.view.EscortFragment
import com.example.homepager_older.fragment.housefragment.view.Housefragment
import com.example.homepager_older.fragment.minefragment.view.MineFragment
import com.example.tools.activity.BaseActivity

@Route(path = "/homepager_older/OlderActivity")
class OlderActivity : BaseActivity() {

    private val escortFragment by lazy { EscortFragment() }
    private val houseFragment by lazy { Housefragment() }
    private val mineFragment by lazy { MineFragment() }
    private val fragmentList = arrayListOf<Fragment>()

    override fun onBaseCreate() {
        //        mapView.onCreate(savedInstanceState)
    }

    override fun getFragmentList(): ArrayList<Fragment> {
        fragmentList.add(houseFragment)
        fragmentList.add(escortFragment)
        fragmentList.add(mineFragment)
        return fragmentList
    }

    override fun onResume() {
        super.onResume()
//        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
//        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
//        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
//        mapView.onSaveInstanceState(outState)
    }

    companion object {
        const val FRAGMENTNUMBER = 3
    }
}
