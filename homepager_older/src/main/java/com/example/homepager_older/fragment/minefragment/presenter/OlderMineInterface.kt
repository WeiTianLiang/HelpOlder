package com.example.homepager_older.fragment.minefragment.presenter

import android.view.View
import android.widget.TextView
import com.example.homepager_older.fragment.minefragment.view.MineFragment
import com.example.tools.adapter.MedicineRecyclerAdapter
import com.example.tools.adapter.MyRecyclerViewAdapter

/**
 * 老人 -我的 实现接口
 * @author weitianliang
 */
interface OlderMineInterface {

    fun addChildren(adapter: MyRecyclerViewAdapter)

    fun changeBirthday(view: View)

    fun olderHead(fragment: MineFragment)

    fun changeName(olderName: TextView)

    fun changeBody(olderBody: TextView)

    fun doBack()

    fun addMedicine(adapter: MedicineRecyclerAdapter)

}