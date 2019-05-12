package com.example.homepager_older.fragment.escortfragment.view.view

import android.graphics.Color
import android.os.Bundle
import com.example.homepager_older.R
import com.example.tools.activity.addFragment
import com.example.tools.activity.hideFragment
import com.example.tools.activity.showFragment
import com.example.tools.fragment.BaseFragment
import com.example.tools.fragment.NowOrderFragment
import com.example.tools.fragment.OrderListFragment
import kotlinx.android.synthetic.main.older_escort_fragment.*

/**
 * 老人陪护界面
 * @author weitianliang
 */
class EscortFragment : BaseFragment() {

    private var nickname: String? = null

    private val nowOrderFragment by lazy { NowOrderFragment() }

    private val orderListFragment by lazy { OrderListFragment(100) }

    override fun onViewCreate(savedInstanceState: Bundle?) {
        addFragment(nowOrderFragment, R.id.escortFragment)
        addFragment(orderListFragment, R.id.escortFragment)
        hideFragment(orderListFragment)
    }

    override fun onInflated(savedInstanceState: Bundle?) {
        nowEscort.setTextColor(Color.GRAY)
        nowEscort.setOnClickListener {
            showFragment(nowOrderFragment)
            hideFragment(orderListFragment)
            nowEscort.setTextColor(Color.GRAY)
            escortList.setTextColor(Color.BLACK)
        }

        escortList.setOnClickListener {
            showFragment(orderListFragment)
            hideFragment(nowOrderFragment)
            escortList.setTextColor(Color.GRAY)
            nowEscort.setTextColor(Color.BLACK)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.older_escort_fragment
    }

    fun setNickName(name: String) {
        nickname = name
    }
}