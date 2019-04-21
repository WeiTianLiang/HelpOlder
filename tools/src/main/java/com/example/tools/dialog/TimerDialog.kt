package com.example.tools.dialog

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

/**
 * 时间选择器
 * @author weitianliang
 */

fun createTimerDialog(calendar: Calendar, context: Context) {
    val datePickerDialog = DatePickerDialog(
        context,
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}
