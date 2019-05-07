package com.example.homepager_children.fragment.minefragment.view.presenter

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import com.example.homepager_children.fragment.minefragment.view.view.ChildrenMineFragment
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
class ChildrenMinePresenter(
    private val context: Context
) : ChildrenMineInterface {

    private val addChildrenDialog by lazy { BaseDialog(context, "请输入您亲人的账户编号！！！") }
    private val changeNameDialog by lazy { BaseDialog(context, "请输入您的姓名") }

    // 退出登录
    override fun doBack() {
        jumpActivity("/login/LoginActivity", context as Activity)
    }

    // 改名
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

    // 更改头像
    override fun childrenHead(fragment: ChildrenMineFragment) {
        doGetPicture(fragment = fragment)
    }

    // 添加关系
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
                childrenToOlder3.identity = "儿子"
                adapter.addItem(childrenToOlder3)
            }
        })
    }

    // 设置ID
    override fun setID(childrenID: TextView) {

    }
}