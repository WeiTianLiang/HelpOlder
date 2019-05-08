package com.example.homepager_escort.fragment.minefragment.view.presenter

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import com.example.homepager_escort.fragment.minefragment.view.dialog.TimeDialog
import com.example.homepager_escort.fragment.minefragment.view.view.MineFragment
import com.example.tools.activity.doGetPicture
import com.example.tools.activity.jumpActivity
import com.example.tools.adapter.MyRecyclerViewAdapter
import com.example.tools.dialog.BaseDialog
import com.example.tools.model.ChildrenToOlder

/**
 *
 * @author WeiTianLiang
 */
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EscortMinePresenter(
    private val context: Context
) : EscortMineInterface {

    private val changeNameDialog by lazy { BaseDialog(context, "请输入您的姓名") }
    private val changeAgeDialog by lazy { BaseDialog(context, "请输入您的年龄") }
    private val addChildrenDialog by lazy { BaseDialog(context, "请输入老人的账户编号！！！") }
    private val timeDialog by lazy { TimeDialog(context) }

    override fun setSex(escortSex: TextView) {
        escortSex.text = "16"
    }

    override fun doBack() {
        jumpActivity("/login/LoginActivity", context as Activity)
    }

    override fun changeAge(escortAge: TextView) {
        changeAgeDialog.setCanceledOnTouchOutside(false)
        changeAgeDialog.window.setGravity(Gravity.CENTER)
        changeAgeDialog.show()
        changeAgeDialog.setOnSureClick(object : BaseDialog.OnClick{
            override fun cancelClick() {
                changeAgeDialog.cancel()
            }

            override fun sureClick(text: String) {
                changeAgeDialog.cancel()
                escortAge.text = text
            }
        })
    }

    override fun changeTime(escortTime: TextView) {
        timeDialog.window.setGravity(Gravity.CENTER)
        timeDialog.show()
        timeDialog.setOnClick(object : TimeDialog.OnClick{
            override fun textClick(text: String) {
                timeDialog.cancel()
                escortTime.text = text
            }
        })
    }

    override fun changeName(escortName: TextView) {
        changeNameDialog.setCanceledOnTouchOutside(false)
        changeNameDialog.window.setGravity(Gravity.CENTER)
        changeNameDialog.show()
        changeNameDialog.setOnSureClick(object : BaseDialog.OnClick{
            override fun cancelClick() {
                changeNameDialog.cancel()
            }

            override fun sureClick(text: String) {
                changeNameDialog.cancel()
                escortName.text = text
            }
        })
    }

    override fun escortHead(fragment: MineFragment) {
        doGetPicture(fragment = fragment)
    }

    override fun addOlder(adapter: MyRecyclerViewAdapter) {
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
                childrenToOlder3.identity = "老人"
                adapter.addItem(childrenToOlder3)
            }
        })
    }
}