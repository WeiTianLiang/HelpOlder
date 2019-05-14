package com.example.homepager_escort.fragment.minefragment.view.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.example.homepager_escort.R
import com.example.homepager_escort.activity.EscortActivity
import com.example.homepager_escort.fragment.minefragment.view.presenter.EscortMinePresenter
import com.example.tools.adapter.MyRecyclerViewAdapter
import com.example.tools.fragment.BaseFragment
import com.example.tools.picture.getOrientation
import com.example.tools.picture.rotateImage
import kotlinx.android.synthetic.main.mine_fragment.*
import java.io.FileNotFoundException

/**
 * 陪护-我的
 * @author weitianliang
 */
class MineFragment : BaseFragment() {

    private val presenter by lazy { context?.let { nickname?.let { it1 -> EscortMinePresenter(it, it1) } } }
    private var bitmap: Bitmap? = null
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.with_older_recycler) }
    private var nickname: String? = null

    override fun onViewCreate(savedInstanceState: Bundle?) {
        presenter?.setOlder(recyclerView)
    }

    override fun onInflated(savedInstanceState: Bundle?) {
        presenter?.setData(
            escort_head,
            escort_sex,
            escort_age,
            escort_time,
            escort_name,
            escort_workType,
            escort_workEx,
            escort_isState
        )
        presenter?.getChildrenAdapter(object : EscortMinePresenter.OnGetChildrenAdapter {
            override fun getChildrenAdapter(adapter: MyRecyclerViewAdapter) {
                adapter.setOnDeleteClick(object : MyRecyclerViewAdapter.OnDeleteClick {
                    override fun deleteClick(position: Int) {
                        presenter?.deleteParent(position)
                    }
                })
            }
        })
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

        change_workType.setOnClickListener {
            presenter?.changeWorkType(escort_workType)
        }

        /**
         * 改变时间
         */
        change_time.setOnClickListener {
            presenter?.changeTime(escort_time)
        }

        add_Older.setOnClickListener {
            presenter?.addOlder()
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

    fun setNickName(name: String) {
        nickname = name
    }
}