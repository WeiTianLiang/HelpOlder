package com.example.homepager_escort.fragment.minefragment.view.presenter

import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.homepager_escort.fragment.minefragment.view.view.MineFragment
import de.hdodenhof.circleimageview.CircleImageView

/**
 *
 * @author WeiTianLiang
 */
interface EscortMineInterface {

    fun addOlder()

    fun escortHead(fragment: MineFragment)

    fun changeName(escortName: TextView)

    fun changeTime(escortTime: TextView)

    fun changeAge(escortAge: TextView)

    fun doBack()

    fun setData(
        escort_head: CircleImageView,
        escortSex: TextView,
        escortAge: TextView,
        escortTime: TextView,
        escortName: TextView,
        escortWorkType: TextView,
        escortWorkEx: TextView
    )

    fun changeWorkType(escortWorkType: TextView)

    fun setOlder(recyclerView: RecyclerView)

    fun deleteParent(position: Int)

}