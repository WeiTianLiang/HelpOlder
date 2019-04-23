package com.example.homepager_older.fragment.minefragment.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.example.homepager_older.fragment.minefragment.dialog.MedicineDialog
import com.example.homepager_older.fragment.minefragment.view.MineFragment
import com.example.tools.activity.doGetPicture
import com.example.tools.activity.jumpActivity
import com.example.tools.adapter.MedicineRecyclerAdapter
import com.example.tools.adapter.MyRecyclerViewAdapter
import com.example.tools.dialog.BaseDialog
import com.example.tools.dialog.createTimerDialog
import com.example.tools.model.ChildrenToOlder
import com.example.tools.model.MedicineAndCount
import java.text.SimpleDateFormat
import java.util.*

/**
 * 老人 -我的 实现
 * @author weitianliang
 */
@SuppressLint("SimpleDateFormat")
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class OlderMinePresenter(
    private val context: Context
) : OlderMineInterface {

    private val addChildrenDialog by lazy { BaseDialog(context, "请输入您亲人的账户编号！！！") }
    private val changeNameDialog by lazy { BaseDialog(context, "请输入您的姓名") }
    private val calendar by lazy { Calendar.getInstance(Locale.CHINA) }
    private val format by lazy { SimpleDateFormat("yyyy-MM-dd") }
    private val medicineDialog by lazy { MedicineDialog(context) }

    /**
     * 添加关系人
     */
    override fun addChildren(adapter: MyRecyclerViewAdapter) {
        addChildrenDialog.setCanceledOnTouchOutside(false)
        addChildrenDialog.window.setGravity(Gravity.CENTER)
        addChildrenDialog.show()
        addChildrenDialog.setOnSureClick(object : BaseDialog.OnClick{
            override fun cancelClick() {
                addChildrenDialog.cancel()
            }

            override fun sureClick(text: String) {
                addChildrenDialog.cancel()
                // 绑定好获取到用户id
                val childrenToOlder3 = ChildrenToOlder()
                childrenToOlder3.nametext = "小明"
                childrenToOlder3.identity = "儿子"
                adapter.addItem(childrenToOlder3)
            }
        })
    }

    /**
     * 改变生日
     */
    override fun changeBirthday(view: View) {
        createTimerDialog(calendar, context)
        (view as TextView).text = format.format(calendar.time)
    }

    /**
     * 改变头像
     */
    override fun olderHead(fragment: MineFragment) {
        doGetPicture(fragment = fragment)
    }

    /**
     * 改名
     */
    override fun changeName(olderName: TextView) {
        changeNameDialog.setCanceledOnTouchOutside(false)
        changeNameDialog.window.setGravity(Gravity.CENTER)
        changeNameDialog.show()
        changeNameDialog.setOnSureClick(object : BaseDialog.OnClick{
            override fun cancelClick() {
                changeNameDialog.cancel()
            }

            override fun sureClick(text: String) {
                changeNameDialog.cancel()
                olderName.text = text
            }
        })
    }

    /**
     * 改健康状况
     */
    override fun changeBody(olderBody: TextView) {
        olderBody.text = if(olderBody.text == "健康") {
            "不健康"
        } else {
            "健康"
        }
    }

    /**
     * 添加药物
     */
    override fun addMedicine(adapter: MedicineRecyclerAdapter) {
        medicineDialog.setCanceledOnTouchOutside(false)
        medicineDialog.window.setGravity(Gravity.CENTER)
        medicineDialog.show()
        medicineDialog.setOnClick(object : MedicineDialog.OnClick{
            override fun cancelClick() {
                medicineDialog.cancel()
            }

            override fun sureClick(medicineName: String, medicineTime: String) {
                medicineDialog.cancel()
                val medicineAndCount = MedicineAndCount()
                medicineAndCount.medicineName = medicineName
                medicineAndCount.medicineCount = medicineTime
                adapter.addItem(medicineAndCount)
            }
        })
    }

    /**
     * 退出登陆
     */
    override fun doBack() {
        jumpActivity("/login/LoginActivity", context as Activity)
    }
}
