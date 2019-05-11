package com.example.homepager_older.fragment.minefragment.presenter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.homepager_older.fragment.minefragment.view.MineFragment
import de.hdodenhof.circleimageview.CircleImageView

/**
 * 老人 -我的 实现接口
 * @author weitianliang
 */
interface OlderMineInterface {

    fun addChildren()

    fun changeBirthday(view: View)

    fun olderHead(fragment: MineFragment)

    fun changeName(olderName: TextView)

    fun changeBody(olderBody: TextView)

    fun doBack()

    fun addMedicine()

    fun setMedicine(recyclerView: RecyclerView)

    fun setChildren(recyclerView: RecyclerView)

    fun setData(imageView: CircleImageView, name: TextView, sex: TextView, birthday: TextView, healthy: TextView, idText: TextView)

    fun changeMedicine(state: Int, position: Int)

    fun deleteMedicine(position: Int)

    fun deleteChildren(position: Int)

}