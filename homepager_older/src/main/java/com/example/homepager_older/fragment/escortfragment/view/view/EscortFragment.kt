package com.example.homepager_older.fragment.escortfragment.view.view

import android.os.Bundle
import com.example.homepager_older.R
import com.example.tools.activity.addFragment
import com.example.tools.activity.replaceFragment
import com.example.tools.fragment.BaseFragment
import kotlinx.android.synthetic.main.older_escort_fragment.*

/**
 *
 * @author weitianliang
 */
class EscortFragment : BaseFragment() {

    private val nowOrderFragment by lazy { NowOrderFragment() }

    private val orderListFragment by lazy { OrderListFragment() }

    override fun onViewCreate(savedInstanceState: Bundle?) {
        addFragment(nowOrderFragment, R.id.escortFragment)
    }

    override fun onInflated(savedInstanceState: Bundle?) {
        nowEscort.setOnClickListener {
            replaceFragment(nowOrderFragment, R.id.escortFragment)
        }

        escortList.setOnClickListener {
            replaceFragment(orderListFragment, R.id.escortFragment)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.older_escort_fragment
    }
}