package com.example.homepager_escort.fragment.minefragment.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.homepager_escort.R
import kotlinx.android.synthetic.main.time_dialog.*

/**
 * 改变陪护工作时间弹窗
 * @author WeiTianLiang
 */
class TimeDialog(
    context: Context
) : Dialog(context, R.style.dialog) {

    private var click: OnClick? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_dialog)

        allDay.setOnClickListener {
            click?.textClick(allDay.text.toString())
        }

        whiteDay.setOnClickListener {
            click?.textClick(whiteDay.text.toString())
        }

        blackDay.setOnClickListener {
            click?.textClick(blackDay.text.toString())
        }
    }

    interface OnClick {
        fun textClick(text: String)
    }

    fun setOnClick(click: OnClick) {
        this.click = click
    }
}