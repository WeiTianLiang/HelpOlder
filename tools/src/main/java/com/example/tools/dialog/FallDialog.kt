package com.example.tools.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.tools.R
import kotlinx.android.synthetic.main.fall_dialog.*

/**
 * 跌倒弹窗
 * @author WeiTianLiang
 */
class FallDialog(
    context: Context
) : Dialog(context, R.style.dialog) {

    private var fallClick: OnFallClick? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fall_dialog)

        if(button.text == "取消警报") {
            button.setOnClickListener {
                // 取消
            }
        } else {
            button.setOnClickListener {
                // 知道
            }
        }
    }

    interface OnFallClick {
        fun buttonClick()
    }

    fun setOnSureClick(fallClick: OnFallClick) {
        this.fallClick = fallClick
    }


}