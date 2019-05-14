package com.example.tools.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TimePicker
import java.util.*

/**
 * 时间选择器
 * @author weitianliang
 */

class TimerDialog : DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var year = 0
    private var month = 0
    private var day = 0

    private var hour = 0
    private var minute = 0

    fun createTimerDialog(calendar: Calendar, context: Context): String {
        val datePickerDialog = DatePickerDialog(
            context,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
        return "$year-$month-$day"
    }

    fun createHoursDialog(calendar: Calendar, context: Context): String {
        val datePickerDialog = TimePickerDialog(
            context,
            this,
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE),
            true
        )
        datePickerDialog.show()
        return "$hour:$minute"
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        hour = p1
        minute = p2
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        year = p1
        month = p2 + 1
        day = p3
    }
}
