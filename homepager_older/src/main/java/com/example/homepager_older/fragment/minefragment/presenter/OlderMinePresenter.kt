package com.example.homepager_older.fragment.minefragment.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.homepager_older.fragment.minefragment.dialog.MedicineDialog
import com.example.homepager_older.fragment.minefragment.model.MedicationModel
import com.example.homepager_older.fragment.minefragment.model.OlderDataModel
import com.example.homepager_older.fragment.minefragment.view.MineFragment
import com.example.tools.activity.doGetPicture
import com.example.tools.activity.jumpActivity
import com.example.tools.adapter.MedicineRecyclerAdapter
import com.example.tools.adapter.MyRecyclerViewAdapter
import com.example.tools.dialog.BaseDialog
import com.example.tools.dialog.createTimerDialog
import com.example.tools.model.BaseStringModel
import com.example.tools.model.ChildrenToOlder
import com.example.tools.model.MedicineAndCount
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import com.example.tools.net.PackageGson
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * 老人 -我的 实现
 * @author weitianliang
 */
@SuppressLint("SimpleDateFormat")
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class OlderMinePresenter(
    private val context: Context,
    private val nickname: String,
    private val id: Int
) : OlderMineInterface {

    private val list = arrayListOf<ChildrenToOlder>()
    private val list1 = arrayListOf<MedicineAndCount>()
    private var medicineAdapter: MedicineRecyclerAdapter? = null
    private val adapter by lazy { MyRecyclerViewAdapter(list, context) }
    private var isHaveMedication = false

    private val request =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetOlderInterface::class.java)

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

    override fun setData(
        imageView: CircleImageView,
        name: TextView,
        sex: TextView,
        birthday: TextView,
        healthy: TextView,
        idText: TextView
    ) {
        val call = request.getOlderData(nickname)
        call.enqueue(object : Callback<OlderDataModel> {
            override fun onResponse(call: Call<OlderDataModel>, response: Response<OlderDataModel>) {
                if(response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        Glide.with(context).load(response.body()!!.data?.imageUrl).into(imageView)
                        name.text = response.body()!!.data?.name

                        var date: Date? = null
                        response.body()!!.data?.birthday?.let { date = Date(it) }
                        if(date != null) birthday.text = date.toString()

                        sex.text = response.body()!!.data?.gender
                        if(response.body()!!.data?.healthStatus == 0) {
                            healthy.text = "健康"
                        } else {
                            healthy.text = "不健康"
                        }
                        idText.text = response.body()!!.data?.parentCode

                        if(response.body()!!.data?.medicine == 1) {
                            isHaveMedication = true
                        }
                    }
                }
            }

            override fun onFailure(call: Call<OlderDataModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun setChildren(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun setMedicine(recyclerView: RecyclerView) {
        val call = request.getMedicationData(nickname)
        call.enqueue(object : Callback<MedicationModel> {
            override fun onResponse(call: Call<MedicationModel>, response: Response<MedicationModel>) {
                if(response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        for(i in 0 until response.body()!!.data?.size!!) {
                            val medicineAndCount = MedicineAndCount()
                            medicineAndCount.medicineName = response.body()!!.data?.get(i)?.medicine
                            medicineAndCount.medicineCount = response.body()!!.data?.get(i)?.medicineTime
                            list1.add(medicineAndCount)
                        }
                        medicineAdapter = MedicineRecyclerAdapter(list1, context)
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter = medicineAdapter
                    }
                }
            }

            override fun onFailure(call: Call<MedicationModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private val addChildrenDialog by lazy { BaseDialog(context, "请输入您亲人的账户编号！！！") }
    private val changeNameDialog by lazy { BaseDialog(context, "请输入您的姓名") }
    private val calendar by lazy { Calendar.getInstance(Locale.CHINA) }
    private val format by lazy { SimpleDateFormat("yyyy-MM-dd") }
    private val medicineDialog by lazy { MedicineDialog(context) }

    /**
     * 添加关系人
     */
    override fun addChildren() {
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

        val map = HashMap<Any, Any>()
        map["birthday"] = format.parse(format.format(calendar.time))
        map["id"] = id
        val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
        val call = request.putOlderData(body)
        call.enqueue(object : Callback<BaseStringModel> {
            override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                if(response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        Toast.makeText(context, "成功更改", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
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

                val map = HashMap<Any, Any>()
                map["name"] = olderName.text.toString()
                map["id"] = id
                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                val call = request.putOlderData(body)
                call.enqueue(object : Callback<BaseStringModel> {
                    override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                        if(response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                Toast.makeText(context, "成功更改", Toast.LENGTH_SHORT).show()
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

    /**
     * 改健康状况
     */
    override fun changeBody(olderBody: TextView) {
        olderBody.text = if(olderBody.text == "健康") {
            "不健康"
        } else {
            "健康"
        }
        val map = HashMap<Any, Any>()
        map["healthStatus"] =  olderBody.text.toString()
        map["id"] = id
        val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
        val call = request.putOlderData(body)
        call.enqueue(object : Callback<BaseStringModel> {
            override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                if(response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        Toast.makeText(context, "成功更改", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<BaseStringModel>, t: Throwable) {
                Toast.makeText(context, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * 添加药物
     */
    override fun addMedicine() {
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
                medicineAdapter?.addItem(medicineAndCount)

                val map = HashMap<Any, Any>()
//                map["count"] =
                map["medicine"] = medicineName
                map["medicineTime"] = medicineTime
                map["nickname"] = nickname
                map["status"] = 1
                map["id"] = id
                val body = RequestBody.create(MediaType.parse("application/json"), PackageGson.PacketGson(map))
                val call = request.postMedicationAdd(body)
                call.enqueue(object : Callback<BaseStringModel> {
                    override fun onResponse(call: Call<BaseStringModel>, response: Response<BaseStringModel>) {
                        if(response.isSuccessful && response.body() != null) {
                            if (response.body()!!.code == "200") {
                                Toast.makeText(context, "成功添加药物", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show()
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

    /**
     * 退出登陆
     */
    override fun doBack() {
        jumpActivity("/login/LoginActivity", context as Activity)
    }
}
