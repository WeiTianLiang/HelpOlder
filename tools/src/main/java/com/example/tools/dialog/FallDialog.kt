package com.example.tools.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.tools.R

/**
 * 跌倒弹窗
 * @author WeiTianLiang
 */
class FallDialog(
    context: Context,
    val text: String,
    val buttonText: String
) : Dialog(context, R.style.dialog) {

    private var fallClick: OnFallClick? = null

    private val text2 by lazy { findViewById<TextView>(R.id.text2) }
    private val button by lazy { findViewById<Button>(R.id.button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fall_dialog)
        text2.text = text
        button.text = buttonText

        if(button.text == "取消警报") {
            button.setOnClickListener {
                fallClick?.buttonClick()
            }
        } else {
            button.setOnClickListener {
                fallClick?.buttonClick()
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