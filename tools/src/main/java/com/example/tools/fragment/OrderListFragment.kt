package com.example.tools.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.example.tools.R
import com.example.tools.fragment.presenter.OrderListPresenter

/**
 * 历史订单数据
 * @author WeiTianLiang
 */
@SuppressLint("ValidFragment")
class OrderListFragment @SuppressLint("ValidFragment") constructor(key: Int, state: String) : BaseFragment() {

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.releaseRecycler) }
    private var nickname: String? = null
    private val presenter by lazy { context?.let { nickname?.let { it1 -> OrderListPresenter(it, it1, key, state) } } }

    override fun onViewCreate(savedInstanceState: Bundle?) {
        presenter?.setListData(recyclerView)
    }

    override fun onInflated(savedInstanceState: Bundle?) {

    }

    override fun getLayoutResId(): Int {
        return R.layout.escort_history_fragment
    }

    fun setNickname(nickname: String?) {
        this.nickname = nickname
    }
}