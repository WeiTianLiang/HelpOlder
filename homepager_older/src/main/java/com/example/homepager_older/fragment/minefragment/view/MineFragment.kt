package com.example.homepager_older.fragment.minefragment.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.homepager_older.R
import com.example.homepager_older.activity.OlderActivity
import com.example.homepager_older.fragment.minefragment.presenter.OlderMinePresenter
import com.example.tools.adapter.MedicineRecyclerAdapter
import com.example.tools.adapter.MyRecyclerViewAdapter
import com.example.tools.fragment.BaseFragment
import com.example.tools.picture.getOrientation
import com.example.tools.picture.rotateImage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.older_mine_fragment.*
import java.io.FileNotFoundException

/**
 * 老人 -我的 fragment
 * @author weitianliang
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("SimpleDateFormat")
class MineFragment : BaseFragment() {

    private var nickname: String? = null
    private val presenetr by lazy { context?.let { nickname?.let { it1 -> OlderMinePresenter(it, it1) } } }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.with_children_recycler) }
    private val medicineRecycler by lazy { findViewById<RecyclerView>(R.id.with_medicine_recycler) }
    private var bitmap: Bitmap? = null

    private val older_head by lazy { findViewById<CircleImageView>(R.id.older_head) }
    private val older_name by lazy { findViewById<TextView>(R.id.older_name) }
    private val older_sex by lazy { findViewById<TextView>(R.id.older_sex) }
    private val older_birthday by lazy { findViewById<TextView>(R.id.older_birthday) }
    private val older_body by lazy { findViewById<TextView>(R.id.older_body) }
    private val older_Id by lazy { findViewById<TextView>(R.id.older_Id) }

    override fun onViewCreate(savedInstanceState: Bundle?) {
        presenetr?.setChildren(recyclerView)
        presenetr?.setMedicine(medicineRecycler)
        presenetr?.setData(older_head, older_name, older_sex, older_birthday, older_body, older_Id)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onInflated(savedInstanceState: Bundle?) {
        presenetr?.getAdapter(object : OlderMinePresenter.OnGetAdapter{
            override fun getAdapter(adapter: MedicineRecyclerAdapter) {
                adapter.setOnChangeClick(object : MedicineRecyclerAdapter.OnChangeClick {
                    override fun changeClick(position: Int) {
                        presenetr?.changeMedicine(1, position)
                    }
                })

                adapter.setOnDeleteClick(object : MedicineRecyclerAdapter.OnDeleteClick {
                    override fun deleteClick(position: Int) {
                        presenetr?.deleteMedicine(position)
                    }
                })
            }
        })

        presenetr?.getChildrenAdapter(object : OlderMinePresenter.OnGetChildrenAdapter {
            override fun getChildrenAdapter(adapter: MyRecyclerViewAdapter) {
                adapter.setOnDeleteClick(object : MyRecyclerViewAdapter.OnDeleteClick {
                    override fun deleteClick(position: Int) {
                        presenetr?.deleteChildren(position)
                    }
                })
            }
        })

        add_children.setOnClickListener {
            presenetr?.addChildren()
        }

        change_birthday.setOnClickListener {
            presenetr?.changeBirthday(older_birthday)
        }

        older_head.setOnClickListener {
            presenetr?.olderHead(this)
        }

        change_name.setOnClickListener {
            presenetr?.changeName(older_name)
        }

        change_body.setOnClickListener {
            presenetr?.changeBody(older_body)
        }

        mine_back.setOnClickListener {
            presenetr?.doBack()
        }

        add_medicine.setOnClickListener {
            presenetr?.addMedicine()
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.older_mine_fragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (123 == requestCode && data != null) {
            try {
                bitmap =
                    BitmapFactory.decodeStream((context as OlderActivity).contentResolver.openInputStream(data.data))
                older_head.setImageBitmap(rotateImage(bitmap!!, getOrientation(context as OlderActivity, data.data)))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    fun setNickName(name: String) {
        nickname = name
    }
}
