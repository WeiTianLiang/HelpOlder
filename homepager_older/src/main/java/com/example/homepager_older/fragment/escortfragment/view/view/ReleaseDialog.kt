package com.example.homepager_older.fragment.escortfragment.view.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.RadioButton
import com.example.homepager_older.R
import kotlinx.android.synthetic.main.release_dialog.*

/**
 * 发布订单弹窗
 * @author WeiTianLiang
 */
class ReleaseDialog(
    context: Context
) : Dialog(context, R.style.dialog) {

    private var click: OnClick? = null
    private var healthyButton: RadioButton? = null
    private var healthyText: String? = null
    private var stateButton: RadioButton? = null
    private var stateText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.release_dialog)

        cancel.setOnClickListener {
            click?.cancelClick()
        }

        healthyGroup.setOnCheckedChangeListener { _, checkedId ->
            healthyButton = findViewById(checkedId)
            healthyText = healthyButton?.text.toString()
        }

        stateGroup.setOnCheckedChangeListener { _, checkedId ->
            stateButton = findViewById(checkedId)
            stateText = stateButton?.text.toString()
        }

        sure.setOnClickListener {
            healthyText?.let { it1 ->
                stateText?.let { it2 ->
                    click?.sureClick(location.text.toString(), manState.text.toString(), manName.text.toString(),
                        it1, it2, start_time.text.toString(), end_time.text.toString(), other.text.toString())
                }
            }
            manName.setText("")
            start_time.text = ""
            end_time.text = ""
            other.setText("")
        }
    }

    interface OnClick {
        fun sureClick(
            location: String,
            manState: String,
            manName: String,
            healthy: String,
            state: String,
            startTime: String,
            endTime: String,
            other: String
        )

        fun cancelClick()
    }

    fun setOnClick(click: OnClick) {
        this.click = click
    }

}