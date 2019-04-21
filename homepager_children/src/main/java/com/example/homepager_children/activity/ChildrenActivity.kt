package com.example.homepager_children.activity

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.homepager_children.fragment.escortfragment.view.ChildrenEscortFragment
import com.example.homepager_children.fragment.hosuefragment.view.ChildrenHouseFragment
import com.example.homepager_children.fragment.minefragment.view.ChildrenMineFragment
import com.example.tools.activity.BaseActivity

@Route(path = "/homepager_children/ChildrenActivity")
class ChildrenActivity : BaseActivity() {

    private val escortFragment by lazy { ChildrenEscortFragment() }
    private val houseFragment by lazy { ChildrenHouseFragment() }
    private val mineFragment by lazy { ChildrenMineFragment() }
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
