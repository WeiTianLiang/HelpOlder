package com.example.tools.fragment.presenter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.tools.adapter.ReleaseRecyclerAdapter
import com.example.tools.fragment.interfaces.GetIssueInterface
import com.example.tools.fragment.model.ParentOrderModel
import com.example.tools.model.OrderListModel
import com.example.tools.net.CreateRetrofit
import com.example.tools.net.FileOperate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author WeiTianLiang
 */
class OrderListPresenter(
    val context: Context,
    val nickname: String,
    val key: Int
) : OrderListInterface {

    private var adapter: ReleaseRecyclerAdapter? = null

    private val parentList = arrayListOf<OrderListModel>()
    val simp by lazy { SimpleDateFormat("yyyy-MM-dd hh:mm") }

    val request =
        CreateRetrofit.requestRetrofit(FileOperate.readFile(context)).create(GetIssueInterface::class.java)

    override fun setListData(recyclerView: RecyclerView) {
        val call = request.getParentOrder(nickname)
        call.enqueue(object : Callback<ParentOrderModel> {
            override fun onResponse(call: Call<ParentOrderModel>, response: Response<ParentOrderModel>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.code == "200") {
                        for(i in 0 until response.body()!!.data?.size!!) {
                            val orderListModel = OrderListModel()
                            orderListModel.orderNumber = response.body()!!.data?.get(i)?.orderNo
                            orderListModel.orderLocation = response.body()!!.data?.get(i)?.address
                            orderListModel.orderName = response.body()!!.data?.get(i)?.parentName
                            orderListModel.orderTime = response.body()!!.data?.get(i)?.escortStart?.let { simp.format(Date(
                                it
                            )) }.toString() + " 到 " + response.body()!!.data?.get(i)?.escortEnd?.let { simp.format(
                                Date(it)
                            ) }.toString()
                            orderListModel.orderState = if(response.body()!!.data?.get(i)?.escortType == 0) {
                                "临时陪护"
                            } else {
                                "长期陪护"
                            }
                            orderListModel.id = response.body()!!.data?.get(i)?.id
                            parentList.add(orderListModel)
                            adapter = ReleaseRecyclerAdapter(parentList, context, key, nickname)
                            recyclerView.layoutManager = LinearLayoutManager(context)
                            recyclerView.adapter = adapter
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ParentOrderModel>, t: Throwable) {
                Toast.makeText(context, "网络有问题!!!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}