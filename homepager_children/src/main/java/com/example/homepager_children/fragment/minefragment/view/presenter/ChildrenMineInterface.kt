package com.example.homepager_children.fragment.minefragment.view.presenter

import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.homepager_children.fragment.minefragment.view.view.ChildrenMineFragment
import de.hdodenhof.circleimageview.CircleImageView

/**
 *
 * @author WeiTianLiang
 */
interface ChildrenMineInterface {

    fun addOlder()

    fun childrenHead(fragment: ChildrenMineFragment)

    fun changeName(olderName: TextView)

    fun doBack()

    fun setData(children_head: CircleImageView, children_name: TextView, children_Id: TextView)

    fun setParent(recyclerView: RecyclerView)

    fun deleteParent(position: Int)

}