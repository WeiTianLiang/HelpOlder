package com.example.homepager_children.fragment.minefragment.view.presenter

import android.widget.TextView
import com.example.homepager_children.fragment.minefragment.view.view.ChildrenMineFragment
import com.example.tools.adapter.MyRecyclerViewAdapter

/**
 *
 * @author WeiTianLiang
 */
interface ChildrenMineInterface {

    fun addOlder(adapter: MyRecyclerViewAdapter)

    fun childrenHead(fragment: ChildrenMineFragment)

    fun changeName(olderName: TextView)

    fun doBack()

    fun setID(childrenID: TextView)

}