package com.example.homepager_children.fragment.minefragment.view.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.homepager_children.R
import com.example.homepager_children.activity.ChildrenActivity
import com.example.homepager_children.fragment.minefragment.view.presenter.ChildrenMinePresenter
import com.example.tools.adapter.MyRecyclerViewAdapter
import com.example.tools.fragment.BaseFragment
import com.example.tools.picture.getOrientation
import com.example.tools.picture.rotateImage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.children_mine_fragment.*
import java.io.FileNotFoundException

/**
 * 子女 - 我的 fragment
 * @author weitianliang
 */
class ChildrenMineFragment : BaseFragment() {

    private var bitmap: Bitmap? = null
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.with_older_recycler) }
    private val presenter by lazy { context?.let { nickname?.let { it1 -> ChildrenMinePresenter(it, it1) } } }
    private var nickname: String? = null
    private var child_Id = -1

    private val children_head by lazy { findViewById<CircleImageView>(R.id.children_head) }
    private val children_ID by lazy { findViewById<TextView>(R.id.children_ID) }
    private val children_name by lazy { findViewById<TextView>(R.id.children_name) }

    override fun onViewCreate(savedInstanceState: Bundle?) {
        presenter?.setParent(recyclerView)
        presenter?.setData(children_head, children_name, children_ID)
    }

    override fun onInflated(savedInstanceState: Bundle?) {
        presenter?.getChildrenAdapter(object : ChildrenMinePresenter.OnGetChildrenAdapter {
            override fun getChildrenAdapter(adapter: MyRecyclerViewAdapter) {
                adapter.setOnDeleteClick(object : MyRecyclerViewAdapter.OnDeleteClick {
                    override fun deleteClick(position: Int) {
                        presenter?.deleteParent(position)
                    }
                })
            }
        })
        add_children.setOnClickListener {
            presenter?.addOlder()
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

    fun setNickName(name: String) {
        nickname = name
    }

    fun setID(id: Int) {
        child_Id = id
    }
}
