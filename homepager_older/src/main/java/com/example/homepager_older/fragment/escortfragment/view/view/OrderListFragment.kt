package com.example.homepager_older.fragment.escortfragment.view.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.homepager_older.R
import com.example.homepager_older.fragment.escortfragment.view.model.HistoryListModel
import com.example.tools.fragment.BaseFragment

/**
 * 历史订单数据
 * @author WeiTianLiang
 */
class OrderListFragment : BaseFragment() {

    private val list = arrayListOf<HistoryListModel>()
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.releaseRecycler) }
    private val adapter by lazy { context?.let { ReleaseRecyclerAdapter(list, it) } }

    init {
        val historyListModel = HistoryListModel()
        historyListModel.historyName = "qweqwe"
        historyListModel.historyNumber = "23123123412341234"
        historyListModel.historyTime = "asdasdasdasd"
        list.add(historyListModel)
        val historyListModel1 = HistoryListModel()
        historyListModel1.historyName = "qasdf"
        historyListModel1.historyNumber = "23123123412341234"
        historyListModel1.historyTime = "asdasdasdasd"
        list.add(historyListModel1)
        val historyListModel2 = HistoryListModel()
        historyListModel2.historyName = "zxcvzxcve"
        historyListModel2.historyNumber = "23123123412341234"
        historyListModel2.historyTime = "asdasdasdasd"
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