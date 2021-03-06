package com.example.homepager_escort.fragment.minefragment.view.presenter

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.homepager_escort.fragment.minefragment.view.dialog.TimeDialog
import com.example.homepager_escort.fragment.minefragment.view.model.EscortModel
import com.example.homepager_escort.fragment.minefragment.view.model.EscortParentModel
import com.example.homepager_escort.fragment.minefragment.view.model.FallModel
import com.example.homepager_escort.fragment.minefragment.view.view.MineFragment
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
class EscortMinePresenter(
    private val context: Context,
    private val nickname: String
) : EscortMineInterface {

    private val changeNameDialog by lazy { BaseDialog(context, "请输入您的姓名") }
    private val changeAgeDialog by lazy { BaseDialog(context, "请输入您的年龄") }
    private val addChildrenDialog by lazy { BaseDialog(context, "请输入老人的账号名！！！") }
    private val timeDialog by lazy { TimeDialog(context) }
    private val list = arrayListOf<ChildrenToOlder>()
    private var adapter: MyRecyclerViewAdapter? = null
    private var id: Int = -1
    private val parentNickname = arrayListOf<String>()
    private val timer = Timer()

    private val request =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetEscortInterface::class.java)

    override fun setData(
        escort_head: CircleImageView,
        escortSex: TextView,
        escortAge: TextView,
        escortTime: TextView,
        escortName: TextView,
        escortWorkType: TextView,
        escortWorkEx: TextView,
        escortIsState: TextView
    ) {
        val call = request.getEscortData(nickname)
        call.enqueue(object : Callback<EscortModel> {
            override fun onResponse(call: Call<EscortModel>, response: Response<EscortModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        Glide.with(context).load(response.body()!!.data?.imageUrl).into(escort_head)
                        escortName.text = response.body()!!.data?.name
                        escortSex.text = response.body()!!.data?.gender
                        escortAge.text = response.body()!!.data?.age.toString()
                        escortTime.text = response.body()!!.data?.workTime
                        if (response.body()!!.data?.workType == "1") {
                            escortWorkType.text = "兼职"
                        } else {
                            escortWorkType.text = "全职"
                        }
                        escortWorkEx.text = response.body()!!.data?.workExperience + "年"
                        escortIsState.text = if(response.body()!!.data?.cardStatus == 0) {
                            "未通过审核"
                        } else {
                            "已通过审核"
                        }
                    }
                }
            }

            override fun onFailure(call: Call<EscortModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun setOlder(recyclerView: RecyclerView) {
        val call = request.getOlderData(nickname)
        call.enqueue(object : Callback<EscortParentModel> {
            override fun onResponse(call: Call<EscortParentModel>, response: Response<EscortParentModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        response.body()!!.data?.parentList?.size?.let {
                            for (i in 0 until it) {
                                response.body()!!.data?.parentList?.get(i)?.nickname?.let { it1 ->
                                    parentNickname.add(it1)
                                }
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
                            timer.schedule(task, 100, 120000)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<EscortParentModel>, t: Throwable) {

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
        val call = request.deleteParentDate(parentNickname[position], nickname)
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

    override fun doBack() {
        jumpActivity("/login/LoginActivity", context as Activity)
    }

    override fun changeAge(escortAge: TextView) {
        changeAgeDialog.setCanceledOnTouchOutside(false)
        changeAgeDialog.window.setGravity(Gravity.CENTER)
        changeAgeDialog.show()
        changeAgeDialog.setOnSureClick(object : BaseDialog.OnClick {
            override fun cancelClick() {
                changeAgeDialog.cancel()
            }

            override fun sureClick(text: String) {
                changeAgeDialog.cancel()
                escortAge.text = text

                val map = HashMap<Any, Any>()
                map["age"] = text
                map["id"] = id
                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                val call = request.putEscortData(body)
                call.enqueue(object : Callback<BaseStringModel> {
                    override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                Toast.makeText(context, "更改成功", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "更改失败", Toast.LENGTH_SHORT).show()
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

    override fun changeWorkType(escortWorkType: TextView) {
        val x = if (escortWorkType.text == "全职") {
            escortWorkType.text = "兼职"
            1
        } else {
            escortWorkType.text = "全职"
            0
        }

        val map = HashMap<Any, Any>()
        map["workType"] = x.toString()
        map["id"] = id
        val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
        val call = request.putEscortData(body)
        call.enqueue(object : Callback<BaseStringModel> {
            override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        Toast.makeText(context, "更改成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "更改失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun changeTime(escortTime: TextView) {
        timeDialog.window.setGravity(Gravity.CENTER)
        timeDialog.show()
        timeDialog.setOnClick(object : TimeDialog.OnClick {
            override fun textClick(text: String) {
                timeDialog.cancel()
                escortTime.text = text

                val map = HashMap<Any, Any>()
                map["workTime"] = text
                map["id"] = id
                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                val call = request.putEscortData(body)
                call.enqueue(object : Callback<BaseStringModel> {
                    override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                Toast.makeText(context, "更改成功", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "更改失败", Toast.LENGTH_SHORT).show()
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

    override fun changeName(escortName: TextView) {
        changeNameDialog.setCanceledOnTouchOutside(false)
        changeNameDialog.window.setGravity(Gravity.CENTER)
        changeNameDialog.show()
        changeNameDialog.setOnSureClick(object : BaseDialog.OnClick {
            override fun cancelClick() {
                changeNameDialog.cancel()
            }

            override fun sureClick(text: String) {
                changeNameDialog.cancel()
                escortName.text = text

                val map = HashMap<Any, Any>()
                map["name"] = text
                map["id"] = id
                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                val call = request.putEscortData(body)
                call.enqueue(object : Callback<BaseStringModel> {
                    override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                Toast.makeText(context, "更改成功", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "更改失败", Toast.LENGTH_SHORT).show()
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

    override fun escortHead(fragment: MineFragment) {
        doGetPicture(fragment = fragment)
    }

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

                val map = HashMap<Any, Any?>()
                map["escort"] = nickname
                map["parent"] = text
                map["status"] = 1
                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                val call = request.postEscortDate(body)
                call.enqueue(object : Callback<BaseStringModel> {
                    override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show()
                                val call1 = request.getOlderData(nickname)
                                call1.enqueue(object : Callback<EscortParentModel> {
                                    override fun onResponse(
                                        call: Call<EscortParentModel>,
                                        response: Response<EscortParentModel>
                                    ) {
                                        if (response.isSuccessful && response.body() != null) {
                                            if (response.body()!!.code == "200") {
                                                val list = arrayListOf<ChildrenToOlder>()
                                                for (i in 0 until response.body()!!.data?.parentList?.size!!) {
                                                    response.body()!!.data?.parentList?.get(i)?.nickname?.let {
                                                        parentNickname.add(it)
                                                    }
                                                    val childrenToOlder = ChildrenToOlder()
                                                    childrenToOlder.nametext =
                                                        response.body()!!.data?.parentList?.get(i)?.name
                                                    childrenToOlder.identity =
                                                        response.body()!!.data?.parentList?.get(i)?.gender
                                                    list.add(childrenToOlder)
                                                }
                                                adapter?.upadte(list)
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<EscortParentModel>, t: Throwable) {

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