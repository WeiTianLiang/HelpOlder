package com.example.tools.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.tools.R

/**
 * 跌倒弹窗
 * @author WeiTianLiang
 */
class FallDialog(
    context: Context
) : Dialog(context, R.style.dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fall_dialog)
    }

}