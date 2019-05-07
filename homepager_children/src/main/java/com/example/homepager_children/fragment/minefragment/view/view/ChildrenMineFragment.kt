package com.example.homepager_children.fragment.minefragment.view.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.homepager_children.R
import com.example.homepager_children.activity.ChildrenActivity
import com.example.homepager_children.fragment.minefragment.view.presenter.ChildrenMinePresenter
import com.example.tools.adapter.MyRecyclerViewAdapter
import com.example.tools.fragment.BaseFragment
import com.example.tools.model.ChildrenToOlder
import com.example.tools.picture.getOrientation
import com.example.tools.picture.rotateImage
import kotlinx.android.synthetic.main.children_mine_fragment.*
import java.io.FileNotFoundException

/**
 * 子女 - 我的 fragment
 * @author weitianliang
 */
class ChildrenMineFragment : BaseFragment() {

    private val list = arrayListOf<ChildrenToOlder>()
    private val adapter by lazy { context?.let { MyRecyclerViewAdapter(list, it) } }
    private var bitmap: Bitmap? = null
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.with_older_recycler) }
    private val presenter by lazy { context?.let { ChildrenMinePresenter(it) } }

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
        add_children.setOnClickListener {
            adapter?.let { it1 -> presenter?.addOlder(it1) }
        }

        change_name.setOnClickListener {
            presenter?.changeName(children_name)
        }

        children_head.setOnClickListener {
            presenter?.childrenHead(this)
        }

        mine_back.setOnClickListener {
            presenter?.doBack()
        }

        presenter?.setID(children_ID)
    }

    override fun getLayoutResId(): Int {
        return R.layout.children_mine_fragment
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (123 == requestCode && data != null) {
            try {
                bitmap =
                    BitmapFactory.decodeStream((context as ChildrenActivity).contentResolver.openInputStream(data.data))
                children_head.setImageBitmap(rotateImage(bitmap!!, getOrientation(context as ChildrenActivity, data.data)))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}
