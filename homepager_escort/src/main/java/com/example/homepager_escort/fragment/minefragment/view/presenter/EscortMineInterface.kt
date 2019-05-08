package com.example.homepager_escort.fragment.minefragment.view.presenter

import android.widget.TextView
import com.example.homepager_escort.fragment.minefragment.view.view.MineFragment
import com.example.tools.adapter.MyRecyclerViewAdapter

/**
 *
 * @author WeiTianLiang
 */
interface EscortMineInterface {

    fun addOlder(adapter: MyRecyclerViewAdapter)

    fun escortHead(fragment: MineFragment)

    fun changeName(escortName: TextView)

    fun changeTime(escortTime: TextView)

    fun changeAge(escortAge: TextView)

    fun doBack()

    fun setSex(escortSex: TextView)

}