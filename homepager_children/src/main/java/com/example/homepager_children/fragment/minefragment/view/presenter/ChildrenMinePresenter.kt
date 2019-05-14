package com.example.homepager_children.fragment.minefragment.view.presenter

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.homepager_children.fragment.minefragment.view.model.ChildModel
import com.example.homepager_children.fragment.minefragment.view.model.ChildParentModel
import com.example.homepager_children.fragment.minefragment.view.model.FallModel
import com.example.homepager_children.fragment.minefragment.view.view.ChildrenMineFragment
import com.example.tools.activity.doGetPicture
import com.example.tools.activity.jumpActivity
import com.example.tools.adapter.MyRecyclerViewAdapter
import com.example.tools.dialog.BaseDialog
import com.example.tools.dialog.FallDialog
import com.example.tools.model.BaseStringModel
import com.example.tools.model.ChildrenToOlder
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import com.example.tools.net.PackageGson
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 *
 * @author WeiTianLiang
 */
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ChildrenMinePresenter(
    private val context: Context,
    private val nickname: String
) : ChildrenMineInterface {

    private var childCode: String? = null
    private val addChildrenDialog by lazy { BaseDialog(context, "请输入您亲人的账户编号！！！") }
    private val changeNameDialog by lazy { BaseDialog(context, "请输入您的姓名") }
    private val parentCode = arrayListOf<String>()
    private var adapter: MyRecyclerViewAdapter? = null
    private val list = arrayListOf<ChildrenToOlder>()
    private var id: Int = -1
    private val parentNickname = arrayListOf<String>()

    private val request =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetChildInterface::class.java)

    override fun setData(children_head: CircleImageView, children_name: TextView, children_Id: TextView) {
        val call = request.getChildData(nickname)
        call.enqueue(object : Callback<ChildModel> {
            override fun onResponse(call: Call<ChildModel>, response: Response<ChildModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        Glide.with(context).load(response.body()!!.data?.imageUrl).into(children_head)
                        children_Id.text = response.body()!!.data?.childCode
                        children_name.text = response.body()!!.data?.name
                        childCode = response.body()!!.data?.childCode
                        id = response.body()!!.data?.id!!
                    }
                }
            }

            override fun onFailure(call: Call<ChildModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun setParent(recyclerView: RecyclerView) {
        val call = request.getParentData(nickname)
        call.enqueue(object : Callback<ChildParentModel> {
            override fun onResponse(call: Call<ChildParentModel>, response: Response<ChildParentModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200" && response.body()!!.data != null) {
                        for (i in 0 until response.body()!!.data?.parentList?.size!!) {
                            response.body()!!.data?.parentList?.get(i)?.nickname?.let { parentNickname.add(it) }
                            response.body()!!.data?.parentList?.get(i)?.parentCode?.let { parentCode.add(it) }
                            val childrenToOlder = ChildrenToOlder()
                            childrenToOlder.nametext = response.body()!!.data?.parentList?.get(i)?.name
                            if (response.body()!!.data?.parentList?.get(i)?.gender == "男") {
                                childrenToOlder.identity = "父亲"
                            } else {
                                childrenToOlder.identity = "母亲"
                            }
                            list.add(childrenToOlder)
                        }
                        adapter = MyRecyclerViewAdapter(list, context)
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter = adapter
                        childrenAdapter?.getChildrenAdapter(adapter!!)
                    }
                }
            }

            override fun onFailure(call: Call<ChildParentModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private var task: TimerTask = object : TimerTask() {
        override fun run() {
            mhander.sendEmptyMessage(2)
        }
    }

    private val mhander = Handler(Handler.Callback { p0 ->
        if (p0?.what == 1) {
            val call = request.getOlderState(parentNickname[0])
            call.enqueue(object : Callback<FallModel> {
                override fun onResponse(call: Call<FallModel>, response: Response<FallModel>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.code == "200") {
                            val d = response.body()!!.data?.date?.toLong()?.let { Date(it).time }
                            val d1 = Date().time
                            val judge = d1 - d!!
                            if(judge < Date(120000).time) {
                                val dialog = FallDialog(context, "您的老人出现了状况，请及时处理", "确认")
                                dialog.show()
                                dialog.setOnSureClick(object : FallDialog.OnFallClick {
                                    override fun buttonClick() {
                                        dialog.cancel()
                                    }
                                })
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<FallModel>, t: Throwable) {

                }
            })
        }
        false
    })

    override fun deleteParent(position: Int) {
        val call = request.deleteChildrenDate(parentCode[position], childCode!!)
        call.enqueue(object : Callback<BaseStringModel> {
            override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private var childrenAdapter: OnGetChildrenAdapter? = null

    interface OnGetChildrenAdapter {
        fun getChildrenAdapter(adapter: MyRecyclerViewAdapter)
    }

    fun getChildrenAdapter(childrenAdapter: OnGetChildrenAdapter) {
        this.childrenAdapter = childrenAdapter
    }

    // 退出登录
    override fun doBack() {
        jumpActivity("/login/LoginActivity", context as Activity)
    }

    // 改名
    override fun changeName(olderName: TextView) {
        changeNameDialog.setCanceledOnTouchOutside(false)
        changeNameDialog.window.setGravity(Gravity.CENTER)
        changeNameDialog.show()
        changeNameDialog.setOnSureClick(object : BaseDialog.OnClick {
            override fun cancelClick() {
                changeNameDialog.cancel()
            }

            override fun sureClick(text: String) {
                changeNameDialog.cancel()
                olderName.text = text
                val map = HashMap<Any, Any>()
                map["name"] = text
                map["id"] = id
                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                val call = request.putChildDate(body)
                call.enqueue(object : Callback<BaseStringModel> {
                    override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {
                        Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })
    }

    // 更改头像
    override fun childrenHead(fragment: ChildrenMineFragment) {
        doGetPicture(fragment = fragment)
    }

    // 添加关系
    override fun addOlder() {
        addChildrenDialog.setCanceledOnTouchOutside(false)
        addChildrenDialog.window.setGravity(Gravity.CENTER)
        addChildrenDialog.show()
        addChildrenDialog.setOnSureClick(object : BaseDialog.OnClick {
            override fun cancelClick() {
                addChildrenDialog.cancel()
            }

            override fun sureClick(text: String) {
                addChildrenDialog.cancel()
                // 绑定好获取到用户id
                val childrenToOlder3 = ChildrenToOlder()
                childrenToOlder3.nametext = "小明"
                childrenToOlder3.identity = "儿子"
                adapter?.addItem(childrenToOlder3)

                val map = HashMap<Any, Any?>()
                map["child"] = parentCode
                map["parent"] = text
                map["status"] = 1
                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                val call = request.postChildrenDate(body)
                call.enqueue(object : Callback<BaseStringModel> {
                    override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show()
                                val call1 = request.getParentData(nickname)
                                call1.enqueue(object : Callback<ChildParentModel> {
                                    override fun onResponse(
                                        call: Call<ChildParentModel>,
                                        response: Response<ChildParentModel>
                                    ) {
                                        if (response.isSuccessful && response.body() != null) {
                                            if (response.body()!!.code == "200") {
                                                val list = arrayListOf<ChildrenToOlder>()
                                                for (i in 0 until response.body()!!.data?.parentList?.size!!) {
                                                    response.body()!!.data?.parentList?.get(i)
                                                        ?.parentCode?.let { parentCode.add(it) }
                                                    val childrenToOlder = ChildrenToOlder()
                                                    childrenToOlder.nametext =
                                                        response.body()!!.data?.parentList?.get(i)?.name
                                                    if (response.body()!!.data?.parentList?.get(i)?.gender == "男") {
                                                        childrenToOlder.identity = "儿子"
                                                    } else {
                                                        childrenToOlder.identity = "女儿"
                                                    }
                                                    list.add(childrenToOlder)
                                                }
                                                adapter?.upadte(list)
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<ChildParentModel>, t: Throwable) {

                                    }
                                })
                            } else {
                                Toast.makeText(context, "账号不存在", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {
                        Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })
    }
}