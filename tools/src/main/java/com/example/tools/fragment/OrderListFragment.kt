package com.example.tools.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.tools.R
import com.example.tools.adapter.ReleaseRecyclerAdapter
import com.example.tools.model.OrderListModel

/**
 * 历史订单数据
 * @author WeiTianLiang
 */
@SuppressLint("ValidFragment")
class OrderListFragment @SuppressLint("ValidFragment") constructor(key: Int) : BaseFragment() {

    private val list = arrayListOf<OrderListModel>()
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.releaseRecycler) }
    private val adapter by lazy { context?.let { ReleaseRecyclerAdapter(list, it, key) } }

    init {
        val historyListModel = OrderListModel()
        historyListModel.orderName = "qweqwe"
        historyListModel.orderNumber = "23123123412341234"
        historyListModel.orderTime = "asdasdasdasd"
        historyListModel.orderLocation = "asdasdasdasdasdasdas"
        historyListModel.orderState = "健康"
        list.add(historyListModel)
        val historyListModel1 = OrderListModel()
        historyListModel1.orderName = "qasdf"
        historyListModel1.orderNumber = "23123123412341234"
        historyListModel1.orderTime = "asdasdasdasd"
        historyListModel.orderLocation = "asdasdasdasdasdasdas"
        historyListModel.orderState = "健康"
        list.add(historyListModel1)
        val historyListModel2 = OrderListModel()
        historyListModel2.orderName = "zxcvzxcve"
        historyListModel2.orderNumber = "23123123412341234"
        historyListModel2.orderTime = "asdasdasdasd"
        historyListModel.orderLocation = "asdasdasdasdasdasdas"
        historyListModel.orderState = "健康"
        list.add(historyListModel2)
        list.add(historyListModel2)
        list.add(historyListModel2)
        list.add(historyListModel2)
        list.add(historyListModel2)
        list.add(historyListModel2)
    }

    override fun onViewCreate(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onInflated(savedInstanceState: Bundle?) {

    }

    override fun getLayoutResId(): Int {
        return R.layout.escort_history_fragment
    }
}