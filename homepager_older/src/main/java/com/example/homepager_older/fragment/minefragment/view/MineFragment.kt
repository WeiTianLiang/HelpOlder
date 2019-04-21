package com.example.homepager_older.fragment.minefragment.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.homepager_older.R
import com.example.homepager_older.activity.OlderActivity
import com.example.homepager_older.fragment.minefragment.presenter.OlderMinePresenetr
import com.example.tools.adapter.MyRecyclerViewAdapter
import com.example.tools.fragment.BaseFragment
import com.example.tools.model.ChildrenToOlder
import com.example.tools.picture.getOrientation
import com.example.tools.picture.rotateImage
import kotlinx.android.synthetic.main.older_mine_fragment.*
import java.io.FileNotFoundException

/**
 * 老人 -我的 fragment
 * @author weitianliang
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("SimpleDateFormat")
class MineFragment : BaseFragment() {

    private val list = arrayListOf<ChildrenToOlder>()
    private val adapter by lazy { context?.let { MyRecyclerViewAdapter(list, it) } }

    private val presenetr by lazy { context?.let { OlderMinePresenetr(it) } }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.with_children_recycler) }
    private var bitmap: Bitmap? = null

    init {
        val childrenToOlder = ChildrenToOlder()
        childrenToOlder.nametext = "张明"
        childrenToOlder.identity = "女儿"
        list.add(childrenToOlder)
        val childrenToOlder1 = ChildrenToOlder()
        childrenToOlder1.nametext = "张明"
        childrenToOlder1.identity = "女儿"
        list.add(childrenToOlder1)
        val childrenToOlder2 = ChildrenToOlder()
        childrenToOlder2.nametext = "张明"
        childrenToOlder2.identity = "女儿"
        list.add(childrenToOlder2)
        val childrenToOlder3 = ChildrenToOlder()
        childrenToOlder3.nametext = "张明"
        childrenToOlder3.identity = "女儿"
        list.add(childrenToOlder3)
    }

    override fun onViewCreate() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onInflated() {
        add_children.setOnClickListener {
            adapter?.let { it1 -> presenetr?.addChildren(it1) }
        }

        change_birthday.setOnClickListener {
            presenetr?.changeBirthday(older_birthday)
        }

        older_head.setOnClickListener {
            presenetr?.olderHead()
        }

        change_name.setOnClickListener {
            presenetr?.changeName(older_name)
        }

        change_body.setOnClickListener {
            presenetr?.changeBody(older_body)
        }

    }

    override fun getLayoutResId(): Int {
        return R.layout.older_mine_fragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (123 == requestCode && data != null) {
            try {
                bitmap = BitmapFactory.decodeStream((context as OlderActivity).contentResolver.openInputStream(data.data))
                older_head.setImageBitmap(rotateImage(bitmap!!, getOrientation(context as OlderActivity, data.data)))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}
