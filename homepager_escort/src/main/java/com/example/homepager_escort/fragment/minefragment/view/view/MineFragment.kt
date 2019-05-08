package com.example.homepager_escort.fragment.minefragment.view.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.homepager_escort.R
import com.example.homepager_escort.activity.EscortActivity
import com.example.homepager_escort.fragment.minefragment.view.presenter.EscortMinePresenter
import com.example.tools.adapter.MyRecyclerViewAdapter
import com.example.tools.fragment.BaseFragment
import com.example.tools.model.ChildrenToOlder
import com.example.tools.picture.getOrientation
import com.example.tools.picture.rotateImage
import kotlinx.android.synthetic.main.mine_fragment.*
import java.io.FileNotFoundException

/**
 * 陪护-我的
 * @author weitianliang
 */
class MineFragment : BaseFragment() {

    private val presenter by lazy { context?.let { EscortMinePresenter(it) } }
    private val list = arrayListOf<ChildrenToOlder>()
    private val adapter by lazy { context?.let { MyRecyclerViewAdapter(list, it) } }
    private var bitmap: Bitmap? = null
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.with_older_recycler) }

    init {
        val childrenToOlder = ChildrenToOlder()
        childrenToOlder.nametext = "张明"
        childrenToOlder.identity = "父亲"
        list.add(childrenToOlder)
        val childrenToOlder1 = ChildrenToOlder()
        childrenToOlder1.nametext = "张明"
        childrenToOlder1.identity = "母亲"
        list.add(childrenToOlder1)
        val childrenToOlder2 = ChildrenToOlder()
        childrenToOlder2.nametext = "张明"
        childrenToOlder2.identity = "父亲"
        list.add(childrenToOlder2)
        val childrenToOlder3 = ChildrenToOlder()
        childrenToOlder3.nametext = "张明"
        childrenToOlder3.identity = "母亲"
        list.add(childrenToOlder3)
    }

    override fun onViewCreate(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onInflated(savedInstanceState: Bundle?) {
        /**
         * 改变头像
         */
        escort_head.setOnClickListener {
            presenter?.escortHead(this)
        }

        /**
         * 改变姓名
         */
        change_name.setOnClickListener {
            presenter?.changeName(escort_name)
        }

        /**
         * 改变年龄
         */
        change_age.setOnClickListener {
            presenter?.changeAge(escort_age)
        }

        /**
         * 改变时间
         */
        change_time.setOnClickListener {
            presenter?.changeTime(escort_time)
        }

        add_Older.setOnClickListener {
            adapter?.let { it1 -> presenter?.addOlder(it1) }
        }

        mine_back.setOnClickListener {
            presenter?.doBack()
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.mine_fragment
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (123 == requestCode && data != null) {
            try {
                bitmap =
                    BitmapFactory.decodeStream((context as EscortActivity).contentResolver.openInputStream(data.data))
                escort_head.setImageBitmap(rotateImage(bitmap!!, getOrientation(context as EscortActivity, data.data)))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}