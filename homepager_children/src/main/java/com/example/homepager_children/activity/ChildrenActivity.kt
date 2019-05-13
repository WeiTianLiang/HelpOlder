package com.example.homepager_children.activity

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.homepager_children.fragment.escortfragment.view.view.ChildrenEscortFragment
import com.example.homepager_children.fragment.hosuefragment.view.view.ChildrenHouseFragment
import com.example.homepager_children.fragment.minefragment.view.view.ChildrenMineFragment
import com.example.tools.FallDeteation.CreateXYZ
import com.example.tools.activity.BaseActivity

@Route(path = "/homepager_children/ChildrenActivity")
class ChildrenActivity : BaseActivity() {

    var nickname: String? = null

    private val escortFragment by lazy { ChildrenEscortFragment() }
    private val houseFragment by lazy { ChildrenHouseFragment() }
    private val mineFragment by lazy { ChildrenMineFragment() }
    private val fragmentList = arrayListOf<Fragment>()
    private val createXYZ by lazy { CreateXYZ(this, "children") }

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
