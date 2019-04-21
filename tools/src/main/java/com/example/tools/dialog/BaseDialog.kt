package com.example.tools.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.tools.R
import kotlinx.android.synthetic.main.add_ch_old_dialog.*

/**
 * 弹出框
 * @author weitianliang
 */
class BaseDialog(
    context: Context,
    val string: String
) : Dialog(context, R.style.dialog) {

    private var click: OnClick? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_ch_old_dialog)

        show.text = string

        cancel.setOnClickListener {
            click?.cancelClick()
        }

        sure.setOnClickListener {
            click?.sureClick(dear_id.text.toString())
            dear_id.setText("")
        }
    }

    interface OnClick {
        fun sureClick(text: String)
        fun cancelClick()
    }

    fun setOnSureClick(click: OnClick) {
        this.click = click
    }

}
