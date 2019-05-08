package com.example.tools.fragment

import android.os.Bundle
import android.view.Gravity
import com.example.tools.R
import com.example.tools.dialog.ReleaseDialog
import kotlinx.android.synthetic.main.escort_isover_fragment.*

/**
 * 当前发布的订单
 * @author WeiTianLiang
 */
class NowOrderFragment : BaseFragment() {

    private val releaseDialog by lazy { context?.let { ReleaseDialog(it) } }

    override fun onViewCreate(savedInstanceState: Bundle?) {

    }

    override fun onInflated(savedInstanceState: Bundle?) {
        escort_push.setOnClickListener {
            releaseDialog?.setCanceledOnTouchOutside(false)
            releaseDialog?.window?.setGravity(Gravity.CENTER)
            releaseDialog?.show()
            releaseDialog?.setOnClick(object : ReleaseDialog.OnClick {
                override fun cancelClick() {
                    releaseDialog?.cancel()
                }

                override fun sureClick(
                    location: String,
                    manState: String,
                    manName: String,
                    healthy: String,
                    state: String,
                    startTime: String,
                    endTime: String,
                    other: String
                ) {
                    releaseDialog?.cancel()
                    // 数据传到服务器
                }
            })
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.escort_isover_fragment
    }
}