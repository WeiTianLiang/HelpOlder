package com.example.tools.dialog

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TimePicker
import com.example.tools.R
import kotlinx.android.synthetic.main.release_dialog.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 发布订单弹窗
 * @author WeiTianLiang
 */
@SuppressLint("SimpleDateFormat")
class ReleaseDialog(
    context: Context
) : Dialog(context, R.style.dialog), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var click: OnClick? = null
    private var healthyButton: RadioButton? = null
    private var healthyText: Int? = null
    private var stateButton: RadioButton? = null
    private var stateText: Int? = null
    private var emergency: RadioButton? = null
    private var emergencyStatus: Int? = null
    private val calendar by lazy { Calendar.getInstance(Locale.CHINA) }
    private val calendar1 by lazy { Calendar.getInstance(Locale.CHINA) }
    private val format by lazy { SimpleDateFormat("yyyy-MM-dd") }
    private val format1 by lazy { SimpleDateFormat("hh:mm") }
    private val format2 by lazy { SimpleDateFormat("yyyy-MM-dd hh:mm") }
    private var starttime = Date()
    private var endtime = Date()
    private var isStart = true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.release_dialog)

        cancel.setOnClickListener {
            click?.cancelClick()
        }

        healthyGroup.setOnCheckedChangeListener { _, checkedId ->
            healthyButton = findViewById(checkedId)
            healthyText = if (healthyButton?.text == "健康") {
                0
            } else {
                1
            }
        }

        stateGroup.setOnCheckedChangeListener { _, checkedId ->
            stateButton = findViewById(checkedId)
            stateText = if (stateButton?.text == "临时陪护") {
                0
            } else {
                1
            }
        }

        man_state.setOnCheckedChangeListener { _, checkedId ->
            emergency = findViewById(checkedId)
            emergencyStatus = if (emergency?.text == "普通陪护") {
                0
            } else {
                1
            }
        }

        sure.setOnClickListener {
            healthyText?.let { it1 ->
                stateText?.let { it2 ->
                    emergencyStatus?.let { it3 ->
                        click?.sureClick(
                            location.text.toString(), it3, manName.text.toString(),
                            it1, it2, format2.parse(start_time.text.toString()).time, format2.parse(end_time.text.toString()).time, other.text.toString()
                        )
                    }
                }
            }
            location.setText("")
            manName.setText("")
            start_time.text = ""
            end_time.text = ""
            other.setText("")
        }

        start_time.setOnClickListener {
            isStart = true
            val timePickerDialog = TimePickerDialog(
                context,
                this,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
            val datePickerDialog = DatePickerDialog(
                context,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        end_time.setOnClickListener {
            isStart = false
            val timePickerDialog = TimePickerDialog(
                context,
                this,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
            val datePickerDialog = DatePickerDialog(
                context,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }

    interface OnClick {
        fun sureClick(
            location: String,
            manState: Int,
            manName: String,
            healthy: Int,
            state: Int,
            startTime: Long,
            endTime: Long,
            other: String
        )

        fun cancelClick()
    }

    fun setOnClick(click: OnClick) {
        this.click = click
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        if(isStart) {
            start_time.text = start_time.text.toString() + "$p1:$p2"
        } else {
            end_time.text = end_time.text.toString() + "$p1:$p2"
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        if(isStart) {
            start_time.text = "$p1-${p2+1}-$p3 "
        } else {
            end_time.text = "$p1-${p2+1}-$p3 "
        }
    }

}